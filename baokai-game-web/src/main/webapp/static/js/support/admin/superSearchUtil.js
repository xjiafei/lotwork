var SuperSerchUtil = function (def) {
    var group = new phoenix.SuperSearchGroup();
    var superSearchItems = {};
    var config = {
        tHeadClass: 'sp-td',
        submitEvent: function () {
            alert('submit');
        }
    };
    var uiConfig = {
        types: {
            text: {
                uiInit: function (tHead) {
                    var titleStr = tHead.find('label').text();
                    var attrs = {
                        name: tHead.attr('inputName'),
                        maxLength: tHead.attr('maxSize')
                    };
                    var item = $('<div class="sp-td-cont sp-td-cont-b">');
                    var title = $('<div class="sp-td-title">');
                    var close = $('<span class="sp-filter-close">');
                    var ul = $('<ul class="sp-filter-cont sp-filter-cont-b">');
                    var li = $('<li>');
                    var div = $('<div class="input-append">');
                    var input = $('<input type="text" class="input w-2"/>');
                    var submitBtn = $('<a class="btn sp-filter-submit"/>');
                    var addClass = tHead.attr('addClass');
                    ul.addClass(addClass);
                    setAttrs(input, attrs);
                    title.append(titleStr);
                    div.append(input);
                    div.append(submitBtn);
                    li.append(div);
                    ul.append(li);
                    item.append(title);
                    item.append(ul);
                    item.append(close);
                    return item;
                },
                setDefaultValue: function (tHead) {
                    var title = tHead.find('.sp-td-title');
                    var input = tHead.find('input[type="text"]');
                    var defaultValue = tHead.attr('defaultValue');
                    if (defaultValue) {
                        tHead.addClass('sp-td-havevalue');
                        title.text(defaultValue);
                        input.val(defaultValue);
                    }
                },
                expands: {}
            },
            select: {
                uiInit: function (tHead) {
                    var select = tHead.find('select');
                    var selectOptions = select.find('option');
                    var titleStr = tHead.find('label').text();
                    var item = $('<div class="sp-td-cont sp-td-cont-b">');
                    var title = $('<div class="sp-td-title">');
                    var close = $('<span class="sp-filter-close">');
                    var div = $('<div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="overflow-y:auto;overflow-x:hidden;max-height:500px;">');
                    var ul = $('<ul>');
                    var addClass = tHead.attr('addClass');
                    div.addClass(addClass);
                    selectOptions.each(function () {
                        var option = $(this);
                        var li = $('<li>');
                        var a = $('<a>');
                        a.text(option.text());
                        li.attr('data-select-id', option.val());
                        li.append(a);
                        ul.append(li);
                    });
                    title.append(titleStr);
                    div.append(ul);
                    item.append(title);
                    item.append(div);
                    item.append(close);
                    return item;
                },
                setDefaultValue: function (tHead) {
                    var title = tHead.find('.sp-td-title');
                    var defaultValue = tHead.attr('defaultValue');
                    if (defaultValue) {
                        tHead.addClass('sp-td-havevalue');
                        var lis = tHead.find('li');
                        lis.each(function () {
                            var li = $(this);
                            if (li.attr('data-select-id') == defaultValue) {
                                li.addClass('current');
                                title.text(li.find('a').text());
                            }
                        });
                    }

                },
                expands: {
                    getFormValue: function () {
                        var me = this, li = me.dom.find('li.current'), v = '', result = {};
                        var hasValue = me.dom.hasClass('sp-td-havevalue');
                        if (li.size() > 0) {
                            v = li.eq(0).attr('data-select-id');
                            result[me.name] = v;
                        } else {
                            result[me.name] = '';
                        }
                        return hasValue?result:'';
                    }
                }
            },
            time: {
                uiInit: function (tHead) {
                    var titleStr = tHead.find('label').text();
                    var inputName = tHead.attr('inputName');
                    var item = $('<div class="sp-td-cont sp-td-cont-b">');
                    var title = $('<div class="sp-td-title">');
                    var close = $('<span class="sp-filter-close">');
                    var ul = $('<ul class="sp-filter-cont sp-filter-cont-b" style="width:270px">');
                    var li = $('<li>');
                    var div = $('<div class="input-append">');
                    var startDateInput = $('<input type="text" class="input w-2" tabindex="1" >');
                    var endDateInput = $('<input type="text" class="input w-2" size="10">');
                    var submitBtn = $('<a class="btn sp-filter-submit"/>');
                    var addClass = tHead.attr('addClass');
                    ul.addClass(addClass);
                    title.append(titleStr);
                    startDateInput.attr('name', inputName + 'Start');
                    endDateInput.attr('name', inputName + 'End');
                    div.append(startDateInput);
                    div.append(' - ');
                    div.append(endDateInput);
                    div.append(submitBtn);
                    li.append(div);
                    ul.append(li);
                    item.append(title);
                    item.append(ul);
                    item.append(close);
                    return item;
                },
                setDefaultValue: function (tHead) {
                    var title = tHead.find('.sp-td-title');
                    var input = tHead.find('input[type="text"]');
                    var defaultStart = tHead.attr('defaultStart');
                    var defaultEnd = tHead.attr('defaultEnd');
                    if (defaultStart || defaultEnd) {
                        tHead.addClass('sp-td-havevalue');
                        title.text(defaultStart ? defaultStart : defaultEnd);
                        input[0].value = (defaultStart ? defaultStart : '');
                        input[1].value = (defaultEnd ? defaultEnd : '');
                    }
                },
                expands: {
                    getFormValue: function () {
                        var me = this, ipts = me.dom.find('input[type="text"]'), name = me.name, result = {};
                        var hasValue = me.dom.hasClass('sp-td-havevalue');
                        result[name + 'Start'] = hasValue?ipts[0].value:'';
                        result[name + 'End'] = hasValue?ipts[1].value:'';
                        return result;
                    }
                }
            }
        }
    };
    if (def) {
        if (def.tHeadClass) {
            config.tHeadClass = def.tHeadClass;
        }
        if (def.submitEvent) {
            config.submitEvent = def.submitEvent;
        }
        if (def.customerTypes) {
            uiConfig.types[def.customerTypes.type] = def.customerTypes.config;
        }
    }
    var tHeads = $('.' + config.tHeadClass);
    var setAttrs = function (input, attrs) {
        for (var key in attrs) {
            input.attr(key, attrs[key]);
        }
    };
    tHeads.each(function () {
        var tHead = $(this);
        var searchType = tHead.attr('searchType');
        var inputName = tHead.attr('inputName');
        var type = uiConfig.types[searchType];
        var def = {
            name: inputName,
            type: searchType,
            el: '[inputName="' + inputName + '"]',
            group: group,
            expands: type.expands
        };
        tHead.html(type.uiInit(tHead));
        superSearchItems[inputName] = new phoenix.SuperSearch(def);
        type.setDefaultValue(tHead);
    });
    group.removeEvent('dataChange');
    group.addEvent('dataChange', function () {
        config.submitEvent();
    });
    return {group: group, items: superSearchItems};
};
