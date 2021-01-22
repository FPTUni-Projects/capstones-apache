/**
 *  File: create-user.js
 *
 */
$(window).on('load', function () {
    USER_INFO.mounted();
});

/**
 * CREATE-USER
 */
const USER_INFO = {
    cookieInfo: {},
    serverName: '',

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            USER_INFO.cookieInfo = response

            if (response.rid !== '0') {
                $('#btnBlockUser').remove()
            }

            return response
        }).then(response => {
            return USER_INFO.getUserInfo()
        }).then(response => {
            USER_INFO.getAllLog()
        })
    },

    getUserInfo () {
        let searchParam = window.location.search
        let userId = searchParam ? searchParam.replace('?id=', '') : ''

        return REST.get('/vi/user/api/v1/get-user-info', {userId: userId})
            .then(response => {
                if (response) {
                    let data = response
                    USER_INFO.serverName = data.serverName

                    let vhostConfig = '&lt;VirtualHost *:80&gt;<br/>' +
                                      '&emsp;&emsp;&emsp;ServerAdmin webmaster@' + data.serverName + '<br/>' +
                                      '&emsp;&emsp;&emsp;DocumentRoot "${SRVROOT}/htdocs/' + data.serverName + '"<br>' +
                                      '&emsp;&emsp;&emsp;ServerName ' + data.serverName + '<br/>' +
                                      '&emsp;&emsp;&emsp;ServerAlias ' + data.serverAlias + '<br/>' +
                                      '&emsp;&emsp;&emsp;ErrorLog "logs/' + data.serverName + '-error.log"<br/>' +
                                      '&emsp;&emsp;&emsp;CustomLog "logs/' + data.serverName + '-access.log" common<br/>' +
                                      '&lt;/VirtualHost&gt;'

                    $('#btnBlockUser').attr('data-user', data.userStatus === '1' ? '0' : '1')
                    $('#btnBlockUser').text(data.userStatus === '1' ? 'Unlock User' : 'Block User')

                    let statusHtml = '<span class="main-badge main-badge-fill main-badge-BADGE_COLOR">STATUS_VALUE</span>'
                    switch (data.userStatus) {
                        case CONSTANT.USER_STATUS.ACTIVE.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'success')
                            break
                        case CONSTANT.USER_STATUS.BLOCKED.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'danger')
                            break
                    }
                    statusHtml = statusHtml.replace('STATUS_VALUE', COMMON.getValueByKey(CONSTANT.USER_STATUS, 'id', 'name', data.userStatus))

                    $('#websiteInfoWrapper').html(`
                        <div style="width: 30%;" id="overviewInfoWrapper">
                            <div class="info-line">  
                                <span class="info-left">Full Name: </span>
                                <span class="info-right">${data.fullName}</span>
                            </div>
                            <div class="info-line">  
                                <span class="info-left">Phone Number: </span>
                                <span class="info-right">${data.phoneNumber}</span>
                            </div>
                            <div class="info-line">  
                                <span class="info-left">Username: </span>
                                <span class="info-right">${data.username}</span>
                            </div>
                            <div class="info-line">  
                                <span class="info-left">Status: </span>
                                <span class="info-right">${statusHtml}</span>
                            </div>
                        </div>
                        <div style="width: 70%;" id="vhostConfigWrapper">
                            <p>VHost Configuration:</p>
                            <code>${vhostConfig}</code>
                        </div>
                    `)
                }
            })
    },

    updateUserStatus (_status) {
        let searchParam = window.location.search
        let userId = searchParam ? searchParam.replace('?id=', '') : ''

        let successMsg = `${_status === '1' ? 'Block' : 'Unlock'} user success`
        let errorMsg = `${_status === '1' ? 'Block' : 'Unlock'} user failed`

        REST.get('/vi/user/api/v1/update-user-status', {
            userId: userId,
            status: _status
        })
        .then(response => {
            if (response) {
                USER_INFO.getUserInfo()

                $('#btnBlockUser').attr('data-user', data.userStatus === '1' ? '0' : '1')
                $('#btnBlockUser').text(_status === '1' ? 'Unlock User' : 'Block User')

                COMMON.toastMsg(successMsg, CONSTANT.MSG_TYPE.SUCCESS)
            } else {
                COMMON.toastMsg(errorMsg, CONSTANT.MSG_TYPE.ERROR)
            }
        }).catch(error => {
            if (error.responseText) {
                COMMON.toastMsg( errorMsg, CONSTANT.MSG_TYPE.ERROR)
            } else {
                USER_INFO.getUserInfo()

                $('#btnBlockUser').attr(_status === '1' ? '0' : '1')
                $('#btnBlockUser').text(_status === '1' ? 'Unlock User' : 'Block User')

                COMMON.toastMsg(successMsg, CONSTANT.MSG_TYPE.SUCCESS)
            }
        })
    },

    getAllLog() {
        REST.get('/vi/log/api/v1/get-all-log', {serverName: USER_INFO.serverName})
            .then(response => {
                if (response && response.length !== 0) {
                    $('#logLst').empty()
                    response.forEach((log, index) => {
                        $('#logLst').append(`<tr>
                            <td style="font-size: 1.25rem;background:${index % 2 === 0 ? '#fff' : '#eee'}; overflow: hidden; word-break: break-all">${log}</td></tr>`)
                    })
                }
            })
    }
}


/* Block User Action */
$('#btnBlockUser').click(function () {
    USER_INFO.updateUserStatus($('#btnBlockUser').attr('data-user'))
})