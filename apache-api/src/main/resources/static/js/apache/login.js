/**
 *  File: login.js
 *
 */

/**
 * LOGIN
 */
const LOGIN = {
    showpass: 0,

    hideValidate(input) {
        $($(input).parent()).removeClass('alert-validate');
    },

    validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    },

    showValidate(input) {
        $($(input).parent()).addClass('alert-validate');
    },


    login () {
        REST.post('/vi/auth/api/v1/auth/login', {
            username: $('#username').val(),
            password: $('#password').val()
        }).then(response => {
            if(response) {
                if (response.status) {
                    location.reload()
                } else {
                    COMMON.toastMsg('Incorrect username or password.', CONSTANT.MSG_TYPE.ERROR)
                }
            }
        }).catch(error => {
            console.log(error)
            COMMON.toastMsg('Incorrect username or password.', CONSTANT.MSG_TYPE.ERROR)
        })
    }
}

/**
 * Event
 */
/*[ Validate ]*/
$('.validate-form').on('submit',function(){
    var check = true;

    $('.validate-input .input100').each(function() {
        if(LOGIN.validate(this) == false){
            LOGIN.showValidate(this);
            check=false;
        }
    })
    return check;
});
$('.validate-form .input100').each(function(){
    $(this).focus(function(){
        LOGIN.hideValidate(this);
    });
});

/*[ Show pass ]*/
$('.btn-show-pass').on('click', function(){
    if(LOGIN.showPass == 0) {
        $(this).next('input').attr('type','text');
        $(this).find('i').removeClass('fa-eye');
        $(this).find('i').addClass('fa-eye-slash');
        LOGIN.showPass = 1;
    } else {
        $(this).next('input').attr('type','password');
        $(this).find('i').removeClass('fa-eye-slash');
        $(this).find('i').addClass('fa-eye');
        LOGIN.showPass = 0;
    }
});

/*[Login]*/
$('#btnLogin').click(function () { LOGIN.login() })
$('#username').keypress(function (e) {
    if (e.keyCode == 13) {
        LOGIN.login()
    }
})
$('#password').keypress(function (e) {
    if (e.keyCode == 13) {
        LOGIN.login()
    }
})

