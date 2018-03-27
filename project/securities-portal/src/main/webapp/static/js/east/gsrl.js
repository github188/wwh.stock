// 加载分页控件
function loadPageNav(dataUrl, pageSize, fun) {
    // 加载分页控件
    loadNav = function () {
        if (_pageCount > 1) {
            var pn = $('PageCont');
            pn.innerHTML = "";

            // 当前页
            var indexSpan = document.createElement("span");
            // 首页
            var homePage = document.createElement("a");
            homePage.target = "_self";
            homePage.setAttribute("href", "#");
            homePage.innerHTML = "首页";
            // 上一页
            var pageUp = document.createElement("a");
            pageUp.target = "_self";
            pageUp.setAttribute("href", "#");
            pageUp.innerHTML = "上一页";
            // 下一页
            var pageDown = document.createElement("a");
            pageDown.target = "_self";
            pageDown.setAttribute("href", "#");
            pageDown.innerHTML = "下一页";
            // 尾页
            var endPage = document.createElement("a");
            endPage.target = "_self";
            endPage.setAttribute("href", "#");
            endPage.innerHTML = "尾页";
            if (_pageIndex == 1) {// 第一页时
                homePage.className = "nolink";
                pageUp.className = "nolink";
                indexSpan.innerHTML = "1";
                pageDown.onclick = function () {
                    _pageIndex = _pageIndex + 1;
                    getData();
                };
                endPage.onclick = function () {
                    _pageIndex = _pageCount;
                    getData();
                };
            } else if (_pageIndex == _pageCount) {// 最后一页时
                homePage.onclick = function () {
                    _pageIndex = 1;
                    getData();
                };
                pageUp.onclick = function () {
                    _pageIndex = _pageIndex - 1;
                    getData();
                };
                indexSpan.innerHTML = _pageCount;
                pageDown.className = "nolink";
                endPage.className = "nolink";
            } else {
                homePage.onclick = function () {
                    _pageIndex = 1;
                    getData();
                };
                pageUp.onclick = function () {
                    _pageIndex = _pageIndex - 1;
                    getData();
                };
                pageDown.onclick = function () {
                    _pageIndex = _pageIndex + 1;
                    getData();
                };
                endPage.onclick = function () {
                    _pageIndex = _pageCount;
                    getData();
                };
                indexSpan.innerHTML = _pageIndex;
            }

            // 十页以内
            if (_pageCount < 11) {
                pn.appendChild(homePage);
                pn.appendChild(pageUp);
                for (var i = 1; i < 1 + parseInt(_pageCount) ; i++) {
                    if (i == _pageIndex) {
                        pn.appendChild(indexSpan);
                        continue;
                    }
                    var a = document.createElement("a");
                    a.target = "_self";
                    a.setAttribute("href", "#");
                    a.innerHTML = i;
                    a.onclick = function () {
                        _pageIndex = parseInt(this.innerHTML);
                        getData();
                    };
                    pn.appendChild(a);
                }
                pn.appendChild(pageDown);
                pn.appendChild(endPage);
            } else {// 十页以上时
                pn.appendChild(homePage);
                pn.appendChild(pageUp);

                if (_pageIndex < 5) {
                    for (var i = 1; i < 7; i++) {
                        if (i == _pageIndex) {
                            pn.appendChild(indexSpan);
                            continue;
                        }
                        var a = document.createElement("a");
                        a.target = "_self";
                        a.setAttribute("href", "#");
                        a.innerHTML = i;
                        a.onclick = function () {
                            _pageIndex = parseInt(this.innerHTML);
                            getData();
                        };
                        pn.appendChild(a);
                    }
                    // 省略span
                    var dSpan = document.createElement("span");
                    dSpan.className = "txt";
                    dSpan.innerHTML = "...";
                    pn.appendChild(dSpan);
                    var lastPage = document.createElement("a");
                    lastPage.target = "_self";
                    lastPage.setAttribute("href", "#");
                    lastPage.innerHTML = _pageCount;
                    lastPage.onclick = function () {
                        _pageIndex = parseInt(_pageCount);
                        getData();
                    };
                    pn.appendChild(lastPage);
                } else if (_pageIndex > 4 && _pageIndex < _pageCount - 3) {
                    var fisrtPage = document.createElement("a");
                    fisrtPage.target = "_self";
                    fisrtPage.setAttribute("href", "#");
                    fisrtPage.innerHTML = 1;
                    fisrtPage.onclick = function () {
                        _pageIndex = parseInt(1);
                        getData();
                    };
                    pn.appendChild(fisrtPage);
                    // 省略span
                    var dSpan = document.createElement("span");
                    dSpan.className = "txt";
                    dSpan.innerHTML = "...";
                    pn.appendChild(dSpan);
                    for (var i = _pageIndex - 2; i < parseInt(_pageIndex) + 3; i++) {
                        if (i == _pageIndex) {
                            pn.appendChild(indexSpan);
                            continue;
                        }
                        var a = document.createElement("a");
                        a.target = "_self";
                        a.setAttribute("href", "#");
                        a.innerHTML = i;
                        a.onclick = function () {
                            _pageIndex = parseInt(this.innerHTML);
                            getData();
                        };
                        pn.appendChild(a);
                    }
                    // 省略span
                    var dSpan = document.createElement("span");
                    dSpan.className = "txt";
                    dSpan.innerHTML = "...";
                    pn.appendChild(dSpan);
                    var lastPage = document.createElement("a");
                    lastPage.target = "_self";
                    lastPage.setAttribute("href", "#");
                    lastPage.innerHTML = _pageCount;
                    lastPage.onclick = function () {
                        _pageIndex = parseInt(_pageCount);
                        getData();
                    };
                    pn.appendChild(lastPage);
                } else {
                    var fisrtPage = document.createElement("a");
                    fisrtPage.target = "_self";
                    fisrtPage.setAttribute("href", "#");
                    fisrtPage.innerHTML = 1;
                    fisrtPage.onclick = function () {
                        _pageIndex = parseInt(1);
                        getData();
                    };
                    pn.appendChild(fisrtPage);
                    // 省略span
                    var dSpan = document.createElement("span");
                    dSpan.className = "txt";
                    dSpan.innerHTML = "...";
                    pn.appendChild(dSpan);
                    for (var i = _pageCount - 5; i < parseInt(_pageCount) + 1; i++) {
                        if (i == _pageIndex) {
                            pn.appendChild(indexSpan);
                            continue;
                        }
                        var a = document.createElement("a");
                        a.target = "_self";
                        a.setAttribute("href", "#");
                        a.innerHTML = i;
                        a.onclick = function () {
                            _pageIndex = parseInt(this.innerHTML);
                            getData();
                        };
                        pn.appendChild(a);
                    }
                }
                pn.appendChild(pageDown);
                pn.appendChild(endPage);
            }
            pn.parentNode.style.display = "block";
        }
        fun();
    };
    // 查看全部数据链接事件处理
    getData = function () {
        var url = dataUrl;
        if (_sr && _sr != '')
            url = dataUrl + "&sr=" + _sr + "&p=" + _pageIndex + "&ps=" + pageSize + "&cb=callback";
        else
            url = dataUrl + "&p=" + _pageIndex + "&ps=" + pageSize + "&cb=callback";

        jQuery.ajax({
            scriptCharset: "utf-8",
            url: url,
            dataType: "jsonp",
            jsonpCallback: "callback",
            beforeSend: function () {
                // 遮罩层
                $('divRef').style.top = jQuery('#mainContent').position().top + "px";
                $('divRef').style.left = jQuery('#mainContent').position().left + "px";
                $('divRef').style.height = $('mainContent').offsetHeight + "px";
                $('divRef').style.width = $('mainContent').offsetWidth + "px";
                $('divRef').style.display = "block";
                // 删除原有数据
                var childs = $('tabBody').children || $('tabBody').childNodes;
                while (childs.length > 0) {
                    $('tabBody').removeChild(childs[childs.length - 1]);
                }
            },
            success: function (data) {
                _data = data;
                _startRow = (_pageIndex - 1) * pageSize;
            },
            error: function () {
                alert("获取数据失败！")
            },
            complete: function () {
                // 编辑分页控件
                loadNav();
                // 遮罩层
                $('divRef').style.display = "none";
            }
        });
    };
    loadNav();
};

// 日期更改事件处理
function updateData(obj) {
    if (document.all)
        document.getElementById('topTit').innerText = "股市日历(" + obj.cal.getNewDateStr() + ")";
    else
        document.getElementById('topTit').textContent = "股市日历(" + obj.cal.getNewDateStr() + ")";
    $('inputDate').value = obj.cal.getNewDateStr();
    searchData(true);
}

// 获取日期月、日
String.prototype.format = function () {
    if (this == "")
        return;
    var date = this.substr(5);
    return date;
}

// 排序处理
function sortData(isOnload, dataUrl, obj) {
    var img = document.getElementById("sortImg");
    if (!isOnload) {
        _sortType = _sortType ? false : true;
        img.style.display = "";
        if (_sortType) {
            _sr = '1';
            img.src = "../images/down.gif";
        } else {
            img.src = "../images/up.gif";
            _sr = '2';
        }

        dataUrl = _sortType ? dataUrl + "&sr=1" : dataUrl + "&sr=2";
        obj.url = dataUrl + "&p=" + _pageIndex + "&ps=50&js=({pages:(pc),data:[(x)]})&cb=callback";

        var clientHeight = 0;
        var scrollHeight = 0;
        if (window.ActiveXObject || "ActiveXObject" in window || navigator.userAgent.indexOf("Firefox") != -1) {// IE FF
            clientHeight = document.documentElement.clientHeight;
            scrollHeight = document.documentElement.scrollHeight;
        } else {
            clientHeight = document.body.clientHeight;
            scrollHeight = document.body.scrollHeight;
        }
        var titTop = document.getElementById("topTit").offsetTop;
        if (scrollHeight - clientHeight >= titTop)
            moveTop(titTop);
    } else {
        img.style.display = "none";
        _startRow = 0;
        _pageIndex = 1;
        _pageCount = 1;
    }
}

// 排序后滚动至表头
function moveTop(titTop) {
    var scroll_top = document.documentElement.scrollTop + document.body.scrollTop;
    if (scroll_top < titTop) {
        window.scrollBy(0, 10);
        scrolldelay = setTimeout('moveTop(' + titTop + ')', 3);
        if (scroll_top >= titTop)
            clearTimeout(scrolldelay);
    }
};