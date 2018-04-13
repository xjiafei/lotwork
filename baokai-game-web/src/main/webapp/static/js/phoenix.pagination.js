(function () {
    //分页插件  结合/pagination.css 样式
    jQuery.fn.pagination = function (maxentries, opts) {
        opts = jQuery.extend({
            items_per_page: 20,
            num_display_entries: 10,
            current_page: 0,
            num_edge_entries: 0,
            link_to: "#",
            prev_text: "上一页",
            next_text: "下一页",
            ellipse_text: "...",
            prev_show_always: true,
            next_show_always: true,
            display_per_page: false,
            callback: function () { return false; }
        }, opts || {});

        return this.each(function () {
            function numPages() {
                return Math.ceil(maxentries / opts.items_per_page);
            }
            function getInterval() {
                var ne_half = Math.ceil(opts.num_display_entries / 2);
                var np = numPages();
                var upper_limit = np - opts.num_display_entries;
                var start = current_page > ne_half ? Math.max(Math.min(current_page - ne_half, upper_limit), 0) : 0;
                var end = current_page > ne_half ? Math.min(current_page + ne_half, np) : Math.min(opts.num_display_entries, np);
                return [start, end];
            }
            function pageSelected(page_id, evt) {
                current_page = page_id;
                drawLinks();
                var continuePropagation = opts.callback(page_id, panel);
                if (!continuePropagation) {
                    if (evt.stopPropagation) {
                        evt.stopPropagation();
                    }
                    else {
                        evt.cancelBubble = true;
                    }
                }
                return continuePropagation;
            }
            function drawLinks() {
                panel.empty();
                var interval = getInterval();
                var np = numPages();
                var getClickHandler = function (page_id) {
                    return function (evt) { return pageSelected(page_id, evt); }
                };
                panel.append($("<span>总共" + maxentries + "笔记录,每页" + opts.items_per_page + "笔,共" + np + "页</span>"));
                var appendItem = function (page_id, appendopts) {
                    page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
                    appendopts = jQuery.extend({ text: page_id + 1, classes: "" }, appendopts || {});
                    if (page_id == current_page) {
                        var lnk = $("<span class='current'>" + (appendopts.text) + "</span>");
                    }
                    else {
                        var lnk = $("<a>" + (appendopts.text) + "</a>")
						.bind("click", getClickHandler(page_id))
						.attr('href', opts.link_to.replace(/__id__/, page_id));
                    }
                    if (appendopts.classes) { lnk.addClass(appendopts.classes); }
                    panel.append(lnk);
                };
                if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
                    appendItem(current_page - 1, { text: opts.prev_text, classes: "prev" });
                }
                if (interval[0] > 0 && opts.num_edge_entries > 0) {
                    var end = Math.min(opts.num_edge_entries, interval[0]);
                    for (var i = 0; i < end; i++) {
                        appendItem(i);
                    }
                    if (opts.num_edge_entries < interval[0] && opts.ellipse_text) {
                        jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                    }
                }
                for (var i = interval[0]; i < interval[1]; i++) {
                    appendItem(i);
                }
                if (interval[1] < np && opts.num_edge_entries > 0) {
                    if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text) {
                        jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                    }
                    var begin = Math.max(np - opts.num_edge_entries, interval[1]);
                    for (var i = begin; i < np; i++) {
                        appendItem(i);
                    }
                }
                if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
                    appendItem(current_page + 1, { text: opts.next_text, classes: "next" });
                }

                if (opts.display_per_page) {
                    panel.append($("<span>每页显示<input id='new_per_page' type='text' value='" + opts.items_per_page + "'>笔&nbsp;<input id='display_per_pageId' type='button' value='确定'></span>"));
                    $("#display_per_pageId").unbind("click").bind("click", function () {
                        if ($("#new_per_page").val() > 1000) {
                            alert("每页记录不能大于1000笔");
                            return;
                        }
                        opts.items_per_page = $("#new_per_page").val();
                        drawLinks();
                    });
                }
            }
            var current_page = opts.current_page;
            maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
            opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
            var panel = jQuery(this);
            this.selectPage = function (page_id) { pageSelected(page_id); };
            this.prevPage = function () {
                if (current_page > 0) {
                    pageSelected(current_page - 1);
                    return true;
                }
                else {
                    return false;
                }
            };
            this.nextPage = function () {
                if (current_page < numPages() - 1) {
                    pageSelected(current_page + 1);
                    return true;
                }
                else {
                    return false;
                }
            };
            drawLinks();
        });
    }

})(jQuery);