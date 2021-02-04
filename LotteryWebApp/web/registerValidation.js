jQuery.validator.addMethod('phoneUK', function (value) {
    return /^\d{2}-\d{4}-\d{7}$/.test(value);
});

jQuery.validator.addMethod('check_password', function (value) {
    return (/[A-Z]/.test(value) && /[a-z]/.test(value) && /[0-9]/.test(value));
});

$(function() {

    $("form[name='registration']").validate({
        rules: {
            firstname: "required",
            lastname: "required",
            username: "required",
            phone: {
                required: true,
                phoneUK: true
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                check_password: true,
                minlength: 8,
                maxlength: 15
            }
        },

        messages: {
            firstname: "Please enter your first name",
            lastname: "Please enter your last name",
            username: "Please enter your username",
            phone: {
                required: "Please provide a phone number",
                phoneUK: "Please enter your phone number in correct format: xx-xxxx-xxxxxxx"
            },
            password: {
                required: "Please provide a password",
                check_password: "Must contain at least: 1 uppercase character, 1 lowercase character, 1 digit",
                minlength: "Your password must be at least 8 characters long",
                maxlength: "Your password can be 15 characters long at maximum"
            },
            email: {
                required: "Please provide an email address",
                email: "Please enter a valid email address"
            }
        }
    });
});
