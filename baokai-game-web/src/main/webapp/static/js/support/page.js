var PageBox = function (def) {
    var param = {
        pageCount: 10,
        pageListCount: 5,
        totalCount: 0,
        currentPage: 1,
        prevPage: 1,
        nextPage: 1,
        totalPage: 1,
        startNo: 1,
        endNo: 1,
        pages: []
    };
    var uiConfig = {
        form: $('#J-search-form'),
        pageWrapper: $('<div class="page-wrapper">'),
        pageRight: $('<div class="page page-right">'),
        prev: $('<a class="prev">上一步</a>'),
        next: $('<a class="next">下一步</a>'),
        pageBtn: $('<input type="button" class="page-btn" value="确 认"/>'),
        pageText: $('<span class="page-text">共0条记录</span>'),
        page: $('<span class="page-few">到第 <input type="text" class="input" name="page"> /1页</span>'),
        pageIn: $('<input type="text" class="input" name="page">'),
        pageItems: [],
        initPageText: function () {
            var item = '<span class="page-text">共[[totalCount]]条记录</span>';
            item = item.split('[[totalCount]]').join(param.totalCount);
            uiConfig.pageText = item;
            return $(item);
        },
        initPage: function () {
            var item = '<span class="page-few">到第 <input type="text" class="input" name="page"> /[[totalPage]]页</span>';
            item = item.split('[[totalPage]]').join(param.totalPage);
            uiConfig.page = $(item);
            uiConfig.pageIn = this.page.find('[name="page"]');
            uiConfig.pageIn.val(param.currentPage);
            return uiConfig.page;
        },
        initPageItems: function () {
            var currentPage = param.currentPage;
            var pages = param.pages;
            var pageItems = [];
            for (var i in pages) {
                var item = '<a index="[[index]]" class="forward">[[index]]</a>';
                var index = pages[i];
                item = item.split('[[index]]').join(index);
                var itemObj = $(item);
                if (index == currentPage) {
                    itemObj.addClass('current');
                }
                pageItems.push(itemObj);
            }
            uiConfig.pageItems = pageItems;
            return pageItems;
        },
        clickPrev: function () {
            uiConfig.pageIn.val(param.prevPage);
            uiConfig.submit();
        },
        clickNext: function () {
            uiConfig.pageIn.val(param.nextPage);
            uiConfig.submit();
        },
        clickPageItem: function () {
            var index = $(this).attr('index');
            uiConfig.pageIn.val(index);
            uiConfig.submit();
        },
        clickPageBtn: function () {
            var page = uiConfig.pageIn.val();
            if(page>param.totalPage){
                uiConfig.pageIn.val(param.totalPage);
            }else if(page<1){
                uiConfig.pageIn.val(1);
            };
            uiConfig.submit();
        },
        submit:function(){
            uiConfig.form.submit();
        }
    };
    if (def) {
        if (def.currentPage) {
            param.currentPage = def.currentPage;
        }
        if (def.totalCount) {
            param.totalCount = def.totalCount;
        }
        if (def.pageCount) {
            param.pageCount = def.pageCount;
        }
        if (def.pageListCount) {
            param.pageListCount = def.pageListCount;
        }
        if (def.wrapper) {
            uiConfig.pageWrapper = def.wrapper;
        }
        if (def.form) {
            uiConfig.form = def.form;
        }
        if (def.clickPrev) {
            uiConfig.clickPrev = def.clickPrev;
        }
        if (def.clickNext) {
            uiConfig.clickNext = def.clickNext;
        }
        if (def.clickPageItem) {
            uiConfig.clickPageItem = def.clickPageItem;
        }
        if (def.clickPageBtn) {
            uiConfig.clickPageBtn = def.clickPageBtn;
        }
        if (def.submitFn) {
            uiConfig.submit = def.submitFn;
        }
    }

    var initPageParam = function () {
        var totalCount = param.totalCount;
        var pageCount = param.pageCount;
        var currentPage = param.currentPage;
        var pageListCount = param.pageListCount;
        var totalPage = Math.ceil(totalCount / pageCount);
        var prevPage = currentPage > 1 ? currentPage - 1 : currentPage;
        var nextPage = currentPage < totalPage ? currentPage + 1 : currentPage;
        var startNo = pageCount * (currentPage - 1) + 1;
        var endNo = param.startNo + pageCount - 1;
        var pages = genPages(currentPage, totalPage, pageListCount);
        param.pages = pages;
        param.totalPage = totalPage;
        param.prevPage = prevPage;
        param.nextPage = nextPage;
        param.startNo = startNo;
        param.endNo = endNo;
    };

    var genPages = function (page, totalPage, count) {
        var pages = [];
        var end = page + Math.floor(count / 2);
        var start = end - count + 1;
        start = start <= 0?1:start;
        end = end>=totalPage?totalPage:end;
        for (var i = start; i <= end; i++) {
            pages.push(i);
        }
        return pages;
    };

    var initPageRight = function () {
        var pageRight = uiConfig.pageRight;
        var pageItems = uiConfig.initPageItems();
        if(param.currentPage>1){
            pageRight.append(uiConfig.prev);
        }
        for (var i in pageItems) {
            var pageItem = pageItems[i];
            pageRight.append(pageItem);
        }
        if(param.currentPage<param.totalPage){
            pageRight.append(uiConfig.next);
        }
        pageRight.append(uiConfig.initPage());
        pageRight.append(uiConfig.pageBtn);
        return pageRight;
    };

    var initEvent = function () {
        uiConfig.prev.bind('click', uiConfig.clickPrev);
        uiConfig.next.bind('click', uiConfig.clickNext);
        uiConfig.pageBtn.bind('click', uiConfig.clickPageBtn);
        uiConfig.pageIn.bind('keydown', function(e) {
            if (e.which == 13) {
                e.preventDefault();
                uiConfig.clickPageBtn();
            }
        });
        var pageItems = uiConfig.pageItems;
        $.each(pageItems, function (i) {
            pageItems[i].bind('click', uiConfig.clickPageItem);
        });
    };

    var initUI = function () {
        var pageWrapper = uiConfig.pageWrapper;
        pageWrapper.append(uiConfig.initPageText());
        pageWrapper.append(initPageRight());
    };

    initPageParam();
    initUI();
    initEvent();

    return {
        pageCount: param.pageCount,
        currentPage: param.currentPage,
        totalPage: param.totalPage,
        prevPage: param.prevPage,
        nextPage: param.nextPage,
        startNo: param.startNo,
        endNo: param.endNo
    };
};
