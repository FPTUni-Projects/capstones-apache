/**
 *  File: change-password.js
 *  @author khal
 */
$(window).on('load', function () {
    CHANGE_PASSWORD.mounted();
});

/**
 * CHANGE_PASSWORD
 */
const CHANGE_PASSWORD = {
    cookieInfo: {},

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            CHANGE_PASSWORD.cookieInfo = response
        })
    },

    changePassword () {
        let oldPassword = $('#userOldPassword').val()
        let newPassword = $('#userNewPassword').val()
        let confirmPassword = $('#userConfirm').val()

        if (!oldPassword) {
            COMMON.toastMsg('Mật khẩu cũ không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!newPassword) {
            COMMON.toastMsg('Mật khẩu mới không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else if (newPassword !== confirmPassword) {
            COMMON.toastMsg('Xác nhận mật khẩu không chính xác.', CONSTANT.MSG_TYPE.WARNING)
        } else {
            let userPassword = {
                id: CHANGE_PASSWORD.cookieInfo.uid,
                oldPassword: oldPassword,
                newPassword: newPassword
            }

            REST.post('/vi/user/api/v1/change-password', userPassword)
                .then(response => {
                    if (response) {
                        COMMON.toastMsg('Đổi mật khẩu thành công.', CONSTANT.MSG_TYPE.SUCCESS)
                        CHANGE_PASSWORD.clearChangePasswordForm()
                    } else {
                        COMMON.toastMsg('Mật khẩu cũ không chính xác', CONSTANT.MSG_TYPE.ERROR)
                        CHANGE_PASSWORD.clearChangePasswordForm()
                    }
                })
                .catch(error => {
                    COMMON.toastMsg('Đổi mật khẩu không thành công.', CONSTANT.MSG_TYPE.ERROR)
                })
        }
    },

    clearChangePasswordForm () {
        $('#userOldPassword').val('')
        $('#userNewPassword').val('')
        $('#userConfirm').val('')
    }
}

/* [Change Password] */
$('#btnChangePassword').click(function () { CHANGE_PASSWORD.changePassword() })

/* [Cancel Create User] */
$('#btnCancelChangePassword').click(function () { CHANGE_PASSWORD.clearChangePasswordForm() })
