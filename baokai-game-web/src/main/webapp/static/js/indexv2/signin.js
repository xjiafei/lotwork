$(function () {
            $( ".topbar .signin a" ).click(function() {
                $( ".topbar .signin" ).addClass("active");
                $( ".top-login" ).show();
             });
             $(".top-login .close-login").click(function () {
                $( ".top-login" ).hide();
                $( ".topbar .signin" ).removeClass("active");
             });



//$(window).bind("load", function() {
//   $(".choose-model").find("[data-value='0.1']").closest(".choose-model").hide();
//});

});
