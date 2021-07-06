/**
 * 页面加载loading
 */
var _LoadingHtml = '<div id="loading"style="display: block;position: fixed;z-index: 400;width: 100%; height: 100%; top: 0; left: 0;text-align: center; font-size: 0.9rem; color: #595758; background-color: #ffffff;">' +
    '<div style=" display: block;position:absolute;cursor1:wait;left:45%;top:45%;width:auto"><i class="fa fa-spinner fa-spin fa-3x fa-fw" aria-hidden="true"></i></div></div>';
//呈现loading效果
document.write(_LoadingHtml);
jQuery(document).ready(function(){
  jQuery(window).load(function(){
    var _mask = $('#loading');
    setTimeout(function () {_mask.hide(); }, 200);
  });
});

