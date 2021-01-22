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
        let serverName = $('#serverName').val()
        let serverAlias = $('#serverAlias').val()
        let role = '1'

        if (!fullName) {
            COMMON.toastMsg('Full name cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!phoneNumber) {
            COMMON.toastMsg('Phone number cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!username) {
            COMMON.toastMsg('Username cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!password) {
            COMMON.toastMsg('Password cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!serverName) {
            COMMON.toastMsg('Server name cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!serverAlias) {
            COMMON.toastMsg('Server alias cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else {
            REST.get('/vi/user/api/v1/check-exist', {
                serverName: serverName,
                serverAlias: serverAlias,
                username: username
            }).then(response => {
                if (response.msg !== '') {
                    COMMON.toastMsg(response.msg, CONSTANT.MSG_TYPE.WARNING)
                } else {
                    let user = {
                        username: username,
                        password: password,
                        fullName: fullName,
                        phoneNumber: phoneNumber,
                        serverName: serverName,
                        serverAlias: serverAlias,
                        roleId: role,
                        userStatus: CONSTANT.USER_STATUS.ACTIVE.id,
                        serverStatus: CONSTANT.SERVER_STATUS.ENABLED.id
                    }

                    REST.post('/vi/user/api/v1/create-user', user)
                        .then(response => {
                            if (response) {
                                COMMON.toastMsg('Create account success.', CONSTANT.MSG_TYPE.SUCCESS)
                                CREATE_USER.clearCreateUserForm()
                            } else {
                                COMMON.toastMsg('Create account failed.', CONSTANT.MSG_TYPE.ERROR)
                            }
                        })
                        .catch(error => {
                            COMMON.toastMsg('Create account failed.', CONSTANT.MSG_TYPE.ERROR)
                        })
                }
            })
        }
    },

    clearCreateUserForm () {
        $('#userFullName').val('')
        $('#userPhoneNumber').val('')
        $('#userUsername').val('')
        $('#userPassword').val('')
        $('#serverName').val('')
        $('#serverAlias').val('')
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