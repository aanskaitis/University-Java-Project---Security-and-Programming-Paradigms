$(function(){
    $("form[name='login']").validate({
        rules: {
            username: "required",
            password: "required"
        },

        messages: {
            username: "Please enter your username",
            password: "Please provide a password"
        }
    });
});
