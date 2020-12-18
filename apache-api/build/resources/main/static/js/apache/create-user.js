/**
 *  File: create-user.js
 *
 */
$(window).on('load', function () {
    CREATE_USER.mounted();
});

/**
 * CREATE-USER
 */
const CREATE_USER = {
    cookieInfo: {},

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            CREATE_USER.cookieInfo = response
        })
    },

    createUser () {
        let fullName = $('#userFullName').val()
        let phoneNumber = $('#userPhoneNumber').val()
        let username = $('#userUsername').val()
        let password = $('#userPassword').val()
        let role = '1'

        if (!fullName) {
            COMMON.toastMsg('Họ và tên không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!phoneNumber) {
            COMMON.toastMsg('Số điện thoại không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!username) {
            COMMON.toastMsg('Tên đăng nhập không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!password) {
            COMMON.toastMsg('Mật khẩu không được để trống.', CONSTANT.MSG_TYPE.WARNING)
        } else {
            let user = {
                username: username,
                password: password,
                fullName: fullName,
                phoneNumber: phoneNumber,
                roleId: role,
                status: '0'
            }

            REST.post('/vi/user/api/v1/create-user', user)
                .then(response => {
                    if (response) {
                        COMMON.toastMsg('Tạo tài khoản cho nhân viên thành công.', CONSTANT.MSG_TYPE.SUCCESS)
                        CREATE_USER.clearCreateUserForm()
                    } else {
                        COMMON.toastMsg('Tạo tài khoản cho nhân viên không thành công.', CONSTANT.MSG_TYPE.ERROR)
                    }
                })
                .catch(error => {
                    COMMON.toastMsg('Tạo tài khoản cho nhân viên không thành công.', CONSTANT.MSG_TYPE.ERROR)
                })
        }
    },

    clearCreateUserForm () {
        $('#userFullName').val('')
        $('#userPhoneNumber').val('')
        $('#userUsername').val('')
        $('#userPassword').val('')
    }
}

/* [Create User] */
$('#btnCreateUser').click(function () { CREATE_USER.createUser() })

/* [Cancel Create User] */
$('#btnCancelCreateUser').click(function () { CREATE_USER.clearCreateUserForm() })

/* [Auto Format Username] */
$('#userUsername').blur(function () {
    let usernameVal = $('#userUsername').val()
    usernameVal = usernameVal.replaceAll(' ', '').toLowerCase().trim()
    $('#userUsername').val(usernameVal)
})