/*
 * WebViewActivity.java
 * SDKLauncher-Android
 *
 * Created by Yonathan Teitelbaum (Mantano) on 2013-07-10.
 */
//  Copyright (c) 2014 Readium Foundation and/or its licensees. All rights reserved.
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
//  1. Redistributions of source code must retain the above copyright notice, this 
//  list of conditions and the following disclaimer.
//  2. Redistributions in binary form must reproduce the above copyright notice, 
//  this list of conditions and the following disclaimer in the documentation and/or 
//  other materials provided with the distribution.
//  3. Neither the name of the organization nor the names of its contributors may be 
//  used to endorse or promote products derived from this software without specific 
//  prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
//  OF THE POSSIBILITY OF SUCH DAMAGE

package com.ascendlearning.jbl.uglys.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.listeners.OnSwipeTouchListener;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.PrefrenceUtil;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.readium.sdk.android.Container;
import org.readium.sdk.android.ManifestItem;
import org.readium.sdk.android.Package;
import org.readium.sdk.android.SpineItem;
import org.readium.sdk.android.launcher.ContainerHolder;
import org.readium.sdk.android.launcher.model.BookmarkDatabase;
import org.readium.sdk.android.launcher.model.OpenPageRequest;
import org.readium.sdk.android.launcher.model.Page;
import org.readium.sdk.android.launcher.model.PaginationInfo;
import org.readium.sdk.android.launcher.model.ReadiumJSApi;
import org.readium.sdk.android.launcher.model.ViewerSettings;
import org.readium.sdk.android.launcher.util.EpubServer;
import org.readium.sdk.android.launcher.util.EpubServer.DataPreProcessor;
import org.readium.sdk.android.launcher.util.HTMLUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.TimerTask;

public class BookViewActivity extends SuperActivity {

    private final boolean quiet = false;

    private static final String TAG = "BookViewActivity";
    private static final String ASSET_PREFIX = "file:///android_asset/readium-shared-js/";
    private static final String READER_SKELETON = "file:///android_asset/readium-shared-js/reader.html";

    // Installs "hook" function so that top-level window (application) can later
    // inject the window.navigator.epubReadingSystem into this HTML document's
    // iframe
    private static final String INJECT_EPUB_RSO_SCRIPT_1 = ""
            + "window.readium_set_epubReadingSystem = function (obj) {"
            + "\nwindow.navigator.epubReadingSystem = obj;"
            + "\nwindow.readium_set_epubReadingSystem = undefined;"
            + "\nvar el1 = document.getElementById(\"readium_epubReadingSystem_inject1\");"
            + "\nif (el1 && el1.parentNode) { el1.parentNode.removeChild(el1); }"
            + "\nvar el2 = document.getElementById(\"readium_epubReadingSystem_inject2\");"
            + "\nif (el2 && el2.parentNode) { el2.parentNode.removeChild(el2); }"
            + "\n};";

    // Iterate top-level iframes, inject global
    // window.navigator.epubReadingSystem if the expected hook function exists (
    // readium_set_epubReadingSystem() ).
    private static final String INJECT_EPUB_RSO_SCRIPT_2 = ""
            + "var epubRSInject =\nfunction(win) {"
            + "\nvar ret = '';"
            + "\nret += win.location.href;"
            + "\nret += ' ---- ';"
            +
            // "\nret += JSON.stringify(win.navigator.epubReadingSystem);" +
            // "\nret += ' ---- ';" +
            "\nif (win.frames)"
            + "\n{"
            + "\nfor (var i = 0; i < win.frames.length; i++)"
            + "\n{"
            + "\nvar iframe = win.frames[i];"
            + "\nret += ' IFRAME ';"
            + "\nif (iframe.readium_set_epubReadingSystem)"
            + "\n{"
            + "\nret += ' EPBRS ';"
            + "\niframe.readium_set_epubReadingSystem(window.navigator.epubReadingSystem);"
            + "\n}" + "\nret += epubRSInject(iframe);" + "\n}" + "\n}"
            + "\nreturn ret;" + "\n};" + "\nepubRSInject(window);";

    // Script tag to inject the "hook" function installer script, added to the
    // head of every epub iframe document
    private static final String INJECT_HEAD_EPUB_RSO_1 = ""
            + "<script id=\"readium_epubReadingSystem_inject1\" type=\"text/javascript\">\n"
            + "//<![CDATA[\n" + INJECT_EPUB_RSO_SCRIPT_1 + "\n" + "//]]>\n"
            + "</script>";
    // Script tag that generates an HTTP request to a fake script => triggers
    // push of window.navigator.epubReadingSystem into this HTML document's
    // iframe
    private static final String INJECT_HEAD_EPUB_RSO_2 = ""
            + "<script id=\"readium_epubReadingSystem_inject2\" type=\"text/javascript\" "
            + "src=\"/%d/readium_epubReadingSystem_inject.js\"> </script>";
    // Script tag to load the mathjax script payload, added to the head of epub
    // iframe documents, only if <math> tags are detected
    private static final String INJECT_HEAD_MATHJAX = "<script type=\"text/javascript\" src=\"/readium_MathJax.js\"> </script>";

    // Location of payloads in the asset folder
    private static final String PAYLOAD_MATHJAX_ASSET = "reader-payloads/MathJax.js";
    private static final String PAYLOAD_ANNOTATIONS_CSS_ASSET = "reader-payloads/annotations.css";

    private final DataPreProcessor dataPreProcessor = new DataPreProcessor() {

        @Override
        public byte[] handle(byte[] data, String mime, String uriPath,
                             ManifestItem item) {
            if (mime == null
                    || (mime != "text/html" && mime != "application/xhtml+xml")) {
                return null;
            }

            if (!quiet)
                Log.d(TAG, "PRE-PROCESSED HTML: " + uriPath);

            String htmlText = new String(data, Charset.forName("UTF-8"));

            // String uuid = mPackage.getUrlSafeUniqueID();
            String newHtml = htmlText; // HTMLUtil.htmlByReplacingMediaURLsInHTML(htmlText,
            // cleanedUrl, uuid);
            // //"PackageUUID"

            // Set up the script tags to add to the head
            String tagsToInjectToHead = INJECT_HEAD_EPUB_RSO_1
                    // Slightly change fake script src url with an
                    // increasing count to prevent caching of the
                    // request
                    + String.format(INJECT_HEAD_EPUB_RSO_2,
                    ++mEpubRsoInjectCounter);
            // Checks for the existance of MathML => request
            // MathJax payload
            if (newHtml.contains("<math") || newHtml.contains("<m:math")) {
                tagsToInjectToHead += INJECT_HEAD_MATHJAX;
            }

            newHtml = HTMLUtil.htmlByInjectingIntoHead(newHtml,
                    tagsToInjectToHead);

            // Log.d(TAG, "HTML head inject: " + newHtml);

            return newHtml.getBytes();
        }
    };

    private WebView mWebview;
    private Container mContainer;
    private String topic;
    private Package mPackage;
    private OpenPageRequest mOpenPageRequestData;
    private ViewerSettings mViewerSettings;
    private ReadiumJSApi mReadiumJSApi;
    private EpubServer mServer;
    private Menu menu;

    private boolean mIsMoAvailable;
    private boolean mIsMoPlaying;
    private int mEpubRsoInjectCounter = 0;
    private ImageButton btnLeft;
    private ImageButton btnRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookview);

        showProgressBarNonCancellable("Loading Book");

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mWebview = (WebView) findViewById(R.id.webview);
        btnLeft = (ImageButton) findViewById(R.id.left);
        btnRight = (ImageButton) findViewById(R.id.right);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReadiumJSApi.openPageLeft();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReadiumJSApi.openPageRight();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && 0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebview.setOnTouchListener(new OnSwipeTouchListener(BookViewActivity.this) {
            public void onSwipeRight() {
                mReadiumJSApi.openPageLeft();
            }

            public void onSwipeLeft() {
                mReadiumJSApi.openPageRight();
            }

        });
        mWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnLeft.setVisibility(View.INVISIBLE);
                        btnRight.setVisibility(View.INVISIBLE);
                    }
                },Constants.TIME_OUT);
                return false;
            }
        });
        initWebView();

        Intent intent = getIntent();
        if (intent.getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mContainer = ContainerHolder.getInstance().get(
                        extras.getLong(Constants.CONTAINER_ID));
                topic = extras.getString(Constants.TOPIC_NAME);

                TextView book_text_view = (TextView) findViewById(R.id.book_text_view);
                TextUtil.insertText(book_text_view, this.topic);
                if (mContainer == null) {
                    finish();
                    return;
                }
                mPackage = mContainer.getDefaultPackage();

                String rootUrl = "http://" + EpubServer.HTTP_HOST + ":"
                        + EpubServer.HTTP_PORT + "/";
                mPackage.setRootUrls(rootUrl, null);

                try {
                    mOpenPageRequestData = OpenPageRequest.fromJSON(extras
                            .getString(Constants.OPEN_PAGE_REQUEST_DATA));
                } catch (JSONException e) {
                    Log.e(TAG,
                            "Constants.OPEN_PAGE_REQUEST_DATA must be a valid JSON object: "
                                    + e.getMessage(), e);
                }
            }
        }

        mServer = new EpubServer(EpubServer.HTTP_HOST, EpubServer.HTTP_PORT,
                mPackage, quiet, dataPreProcessor);
        mServer.startServer();

        // Load the page skeleton
        mWebview.loadUrl(READER_SKELETON);
        mViewerSettings = new ViewerSettings(
                ViewerSettings.SyntheticSpreadMode.AUTO,
                ViewerSettings.ScrollMode.AUTO, TextUtil.getRealFontSize(Integer.parseInt(PrefrenceUtil.getString(Constants.FONT_SIZE, Constants.DEFAULT_FONT))), 20);

        mReadiumJSApi = new ReadiumJSApi(new ReadiumJSApi.JSLoader() {
            @Override
            public void loadJS(String javascript) {
                mWebview.loadUrl(javascript);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.stop();
        mWebview.loadUrl(READER_SKELETON);
        ((ViewGroup) mWebview.getParent()).removeView(mWebview);
        mWebview.removeAllViews();
        mWebview.clearCache(true);
        mWebview.clearHistory();
        mWebview.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_bookmark) {
            mReadiumJSApi.bookmarkCurrentPage();
        } else if (id == R.id.action_font) {
            CustomFontAlertDialog dialog = new CustomFontAlertDialog(BookViewActivity.this, android.R.style.Theme_Holo_Dialog, application);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_bookmarks) {
            Intent intent = new Intent(this, BookmarksActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        this.menu = menu;
        if (!BookmarkDatabase.getInstance().isBookmarked(mContainer.getName(), topic)) {
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));
        } else {
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebview.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebview.onResume();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void initWebView() {
        mWebview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        mWebview.setWebViewClient(new EpubWebViewClient());
        mWebview.setWebChromeClient(new EpubWebChromeClient());

        mWebview.addJavascriptInterface(new EpubInterface(), "LauncherUI");
    }

    public final class EpubWebViewClient extends WebViewClient {

        private static final String HTTP = "http";
        private static final String UTF_8 = "utf-8";

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!quiet)
                Log.d(TAG, "onPageStarted: " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!quiet)
                Log.d(TAG, "onPageFinished: " + url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            if (!quiet)
                Log.d(TAG, "onLoadResource: " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!quiet)
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            return false;
        }

        private void evaluateJavascript(final String script) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!quiet)
                        Log.d(TAG, "WebView evaluateJavascript: " + script + "");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                        if (!quiet)
                            Log.d(TAG, "WebView evaluateJavascript KitKat+ API");

                        mWebview.evaluateJavascript(script,
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String str) {
                                        if (!quiet)
                                            Log.d(TAG,
                                                    "WebView evaluateJavascript RETURN: "
                                                            + str);
                                    }
                                });
                    } else {

                        if (!quiet)
                            Log.d(TAG, "WebView loadUrl() API");

                        mWebview.loadUrl("javascript:var exec = function(){\n"
                                + script + "\n}; exec();");
                    }
                }
            });
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          String url) {
            if (!quiet)
                Log.d(TAG, "-------- shouldInterceptRequest: " + url);

            if (url != null && url != "undefined") {

                String localHttpUrlPrefix = "http://" + EpubServer.HTTP_HOST
                        + ":" + EpubServer.HTTP_PORT;
                boolean isLocalHttp = url.startsWith(localHttpUrlPrefix);

                // Uri uri = Uri.parse(url);
                // uri.getScheme()

                if (url.startsWith("http") && !isLocalHttp) {
                    if (!quiet)
                        Log.d(TAG, "HTTP (NOT LOCAL): " + url);
                    return super.shouldInterceptRequest(view, url);
                }

                String cleanedUrl = cleanResourceUrl(url, false);
                if (!quiet)
                    Log.d(TAG, url + " => " + cleanedUrl);

                if (cleanedUrl
                        .matches("\\/?\\d*\\/readium_epubReadingSystem_inject.js")) {
                    if (!quiet)
                        Log.d(TAG, "navigator.epubReadingSystem inject ...");

                    // Fake script requested, this is immediately invoked after
                    // epubReadingSystem hook is in place,
                    // => execute js on the reader.html context to push the
                    // global window.navigator.epubReadingSystem into the
                    // iframe(s)

                    evaluateJavascript(INJECT_EPUB_RSO_SCRIPT_2);

                    return new WebResourceResponse("text/javascript", UTF_8,
                            new ByteArrayInputStream(
                                    "(function(){})()".getBytes()));
                }

                if (cleanedUrl.matches("\\/?readium_MathJax.js")) {
                    if (!quiet)
                        Log.d(TAG, "MathJax.js inject ...");

                    InputStream is = null;
                    try {
                        is = getAssets().open(PAYLOAD_MATHJAX_ASSET);
                    } catch (IOException e) {

                        Log.e(TAG, "MathJax.js asset fail!");

                        return new WebResourceResponse(null, UTF_8,
                                new ByteArrayInputStream("".getBytes()));
                    }

                    return new WebResourceResponse("text/javascript", UTF_8, is);
                }

                if (cleanedUrl.matches("\\/?readium_Annotations.css")) {
                    if (!quiet)
                        Log.d(TAG, "annotations.css inject ...");

                    InputStream is = null;
                    try {
                        is = getAssets().open(PAYLOAD_ANNOTATIONS_CSS_ASSET);
                    } catch (IOException e) {

                        Log.e(TAG, "annotations.css asset fail!");

                        return new WebResourceResponse(null, UTF_8,
                                new ByteArrayInputStream("".getBytes()));
                    }

                    return new WebResourceResponse("text/css", UTF_8, is);
                }

                String mime = null;
                int dot = cleanedUrl.lastIndexOf('.');
                if (dot >= 0) {
                    mime = EpubServer.MIME_TYPES.get(cleanedUrl.substring(
                            dot + 1).toLowerCase());
                }
                if (mime == null) {
                    mime = "application/octet-stream";
                }

                ManifestItem item = mPackage.getManifestItem(cleanedUrl);
                String contentType = item != null ? item.getMediaType() : null;
                if (mime != "application/xhtml+xml"
                        && mime != "application/xml" // FORCE
                        && contentType != null && contentType.length() > 0) {
                    mime = contentType;
                }

                if (url.startsWith("file:")) {
                    if (item == null) {
                        Log.e(TAG, "NO MANIFEST ITEM ... " + url);
                        return super.shouldInterceptRequest(view, url);
                    }

                    String cleanedUrlWithQueryFragment = cleanResourceUrl(url,
                            true);
                    String httpUrl = "http://" + EpubServer.HTTP_HOST + ":"
                            + EpubServer.HTTP_PORT + "/"
                            + cleanedUrlWithQueryFragment;
                    Log.e(TAG, "FILE to HTTP REDIRECT: " + httpUrl);

                    try {
                        URLConnection c = new URL(httpUrl).openConnection();
                        c.setUseCaches(false);
                        if (mime == "application/xhtml+xml"
                                || mime == "text/html") {
                            c.setRequestProperty(
                                    "Accept-Ranges", "none");
                        }
                        InputStream is = c.getInputStream();
                        return new WebResourceResponse(mime, null, is);
                    } catch (Exception ex) {
                        Log.e(TAG,
                                "FAIL: " + httpUrl + " -- " + ex.getMessage(),
                                ex);
                    }
                }
                if (!quiet)
                    Log.d(TAG, "RESOURCE FETCH ... " + url);
                return super.shouldInterceptRequest(view, url);
            }

            Log.e(TAG, "NULL URL RESPONSE: " + url);
            return new WebResourceResponse(null, UTF_8,
                    new ByteArrayInputStream("".getBytes()));
        }
    }

    private String cleanResourceUrl(String url, boolean preserveQueryFragment) {

        String cleanUrl = null;

        String httpUrl = "http://" + EpubServer.HTTP_HOST + ":"
                + EpubServer.HTTP_PORT;
        if (url.startsWith(httpUrl)) {
            cleanUrl = url.replaceFirst(httpUrl, "");
        } else {
            cleanUrl = (url.startsWith(ASSET_PREFIX)) ? url.replaceFirst(
                    ASSET_PREFIX, "") : url.replaceFirst("file://", "");
        }

        String basePath = mPackage.getBasePath();
        if (basePath.charAt(0) != '/') {
            basePath = '/' + basePath;
        }
        if (cleanUrl.charAt(0) != '/') {
            cleanUrl = '/' + cleanUrl;
        }
        cleanUrl = (cleanUrl.startsWith(basePath)) ? cleanUrl.replaceFirst(
                basePath, "") : cleanUrl;

        if (cleanUrl.charAt(0) == '/') {
            cleanUrl = cleanUrl.substring(1);
        }

        if (!preserveQueryFragment) {
            int indexOfQ = cleanUrl.indexOf('?');
            if (indexOfQ >= 0) {
                cleanUrl = cleanUrl.substring(0, indexOfQ);
            }

            int indexOfSharp = cleanUrl.indexOf('#');
            if (indexOfSharp >= 0) {
                cleanUrl = cleanUrl.substring(0, indexOfSharp);
            }
        }

        return cleanUrl;
    }

    public class EpubWebChromeClient extends WebChromeClient implements
            MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (!quiet)
                Log.d(TAG, "here in on ShowCustomView: " + view);
            super.onShowCustomView(view, callback);
            if (view instanceof FrameLayout) {
                FrameLayout frame = (FrameLayout) view;
                if (!quiet)
                    Log.d(TAG,
                            "frame.getFocusedChild(): "
                                    + frame.getFocusedChild());
                if (frame.getFocusedChild() instanceof VideoView) {
                    VideoView video = (VideoView) frame.getFocusedChild();
                    // frame.removeView(video);
                    // a.setContentView(video);
                    video.setOnCompletionListener(this);
                    video.setOnErrorListener(this);
                    video.start();
                }
            }
        }

        public void onCompletion(MediaPlayer mp) {
            if (!quiet)
                Log.d(TAG, "Video completed");

            // a.setContentView(R.layout.main);
            // WebView wb = (WebView) a.findViewById(R.id.webview);
            // a.initWebView();
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            Log.e(TAG, "MediaPlayer onError: " + what + ", " + extra);
            return false;
        }
    }

    public class EpubInterface {

        @JavascriptInterface
        public void onPaginationChanged(String currentPagesInfo) {
            if (!quiet)
                Log.d(TAG, "onPaginationChanged: " + currentPagesInfo);
            try {
                PaginationInfo paginationInfo = PaginationInfo
                        .fromJson(currentPagesInfo);
                List<Page> openPages = paginationInfo.getOpenPages();
                if (!openPages.isEmpty()) {
                    final Page page = openPages.get(0);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SpineItem spineItem = mPackage.getSpineItem(page
                                    .getIdref());
                            boolean isFixedLayout = spineItem
                                    .isFixedLayout(mPackage);
                            mWebview.getSettings().setBuiltInZoomControls(
                                    isFixedLayout);
                            mWebview.getSettings()
                                    .setDisplayZoomControls(false);
                        }
                    });
                }
            } catch (JSONException e) {
                Log.e(TAG, "" + e.getMessage(), e);
            }
        }

        @JavascriptInterface
        public void onSettingsApplied() {
            if (!quiet)
                Log.d(TAG, "onSettingsApplied");
        }

        @JavascriptInterface
        public void onReaderInitialized() {
            if (!quiet)
                Log.d(TAG, "onReaderInitialized");

            if (!quiet)
                Log.d(TAG, "openPageRequestData: " + mOpenPageRequestData);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mReadiumJSApi.openBook(mPackage, mViewerSettings,
                            mOpenPageRequestData);
                }
            });

        }

        @JavascriptInterface
        public void onContentLoaded() {
            if (!quiet)
                Log.d(TAG, "onContentLoaded");
        }

        @JavascriptInterface
        public void onPageLoaded() {
            if (!quiet)
                Log.d(TAG, "onPageLoaded");
        }

        @JavascriptInterface
        public void onIsMediaOverlayAvailable(String available) {
            if (!quiet)
                Log.d(TAG, "onIsMediaOverlayAvailable:" + available);
            mIsMoAvailable = available.equals("true");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    invalidateOptionsMenu();
                }
            });
            hideProgressBar();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnLeft.setVisibility(View.INVISIBLE);
                    btnRight.setVisibility(View.INVISIBLE);
                }
            },Constants.TIME_OUT);
        }

        @JavascriptInterface
        public void onMediaOverlayStatusChanged(String status) {
            if (!quiet)
                Log.d(TAG, "onMediaOverlayStatusChanged:" + status);
            // this should be real json parsing if there will be more data that
            // needs to be extracted

            if (status.indexOf("isPlaying") > -1) {
                mIsMoPlaying = status.indexOf("\"isPlaying\":true") > -1;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    invalidateOptionsMenu();
                }
            });
        }

        @JavascriptInterface
        public void getBookmarkData(final String bookmarkData) {
            if (!BookmarkDatabase.getInstance().isBookmarked(mContainer.getName(), topic)) {
                try {
                    JSONObject bookmarkJson = new JSONObject(bookmarkData);
                    BookmarkDatabase.getInstance().addBookmark(mContainer.getName(), topic, bookmarkJson.getString("idref"), bookmarkJson.getString("contentCFI"));
                    runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "" + e.getMessage(), e);
                }
            } else {
                BookmarkDatabase.getInstance().deleteBookmark(mContainer.getName(), topic);
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                });

            }


        }
    }

    private class CustomFontAlertDialog extends Dialog {
        UglysApplication app;

        public CustomFontAlertDialog(Context context, int theme, UglysApplication app) {
            super(context, theme);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.app = app;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Context context = getContext();

            // create custom dialog main layout and inflate view in that.
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.custom_font_dialog, null);

            final Button fontsize_minus_btn = (Button) layout.findViewById(R.id.fontsize_minus_btn);
            final Button fontsize_plus_btn = (Button) layout.findViewById(R.id.fontsize_plus_btn);
            final TextView tv_font_size_header = (TextView) layout.findViewById(R.id.tv_font_size_header);

            TextUtil.insertText(tv_font_size_header, context.getString(R.string.font_size));

            final int[] text = app.getFontSize();
            tv_font_size_header.setTextSize(text[0]);

            if (text[0] == Constants.MIN_FONT) {
                fontsize_plus_btn.setEnabled(true);
                fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                fontsize_minus_btn.setEnabled(false);
                fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
            } else if (text[0] == Constants.MAX_FONT) {
                fontsize_minus_btn.setEnabled(true);
                fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                fontsize_plus_btn.setEnabled(false);
                fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
            } else {
                fontsize_minus_btn.setEnabled(true);
                fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                fontsize_plus_btn.setEnabled(true);
                fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
            }
            fontsize_minus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (text[0] >= Constants.MIN_FONT) {
                        fontsize_plus_btn.setEnabled(true);
                        fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                        fontsize_minus_btn.setEnabled(true);
                        fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                        text[0] = text[0] - 2;
                        if (text[0] == Constants.MIN_FONT) {
                            fontsize_minus_btn.setEnabled(false);
                            fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                        }
                        app.setFontSize(text);
                        PrefrenceUtil.setString(Constants.FONT_SIZE, Integer.toString(text[0]));
                        mViewerSettings = new ViewerSettings(ViewerSettings.SyntheticSpreadMode.AUTO, ViewerSettings.ScrollMode.AUTO, TextUtil.getRealFontSize(text[0]), 20);
                        mReadiumJSApi.updateSettings(mViewerSettings);
                        tv_font_size_header.setTextSize(text[0]);
                        showProgressBarNonCancellable("Setting Font");
                    } else {
                        fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                        fontsize_minus_btn.setEnabled(false);
                    }

                }
            });
            fontsize_plus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (text[0] <= Constants.MAX_FONT) {
                        fontsize_plus_btn.setEnabled(true);
                        fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                        fontsize_minus_btn.setEnabled(true);
                        fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                        text[0] = text[0] + 2;
                        if (text[0] == Constants.MAX_FONT) {
                            fontsize_plus_btn.setEnabled(false);
                            fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                        }
                        app.setFontSize(text);
                        PrefrenceUtil.setString(Constants.FONT_SIZE, Integer.toString(text[0]));
                        mViewerSettings = new ViewerSettings(ViewerSettings.SyntheticSpreadMode.AUTO, ViewerSettings.ScrollMode.AUTO, TextUtil.getRealFontSize(text[0]), 20);
                        mReadiumJSApi.updateSettings(mViewerSettings);
                        tv_font_size_header.setTextSize(text[0]);
                        showProgressBarNonCancellable("Setting Font");
                    } else {
                        fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                        fontsize_plus_btn.setEnabled(false);
                    }

                }
            });
            setContentView(layout);

        }

    }
}
