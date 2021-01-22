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

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            USER_INFO.cookieInfo = response

            return response
        }).then(response => {
            USER_INFO.getUserInfo()
        })
    },

    getUserInfo () {
        let searchParam = window.location.search
        let userId = searchParam ? searchParam.replace('?id=', '') : ''

        REST.get('/vi/user/api/v1/get-user-info', {userId: userId})
            .then(response => {
                if (response) {
                    $('#websiteInfoWrapper').html(`
                        <div class="row" style="display: flex; justify-content: space-between">
                            <div>${response.username}</div>
                            <div>${response.serverName}</div>
                            <div>${response.serverAlias}</div>
                        </div>
                    `)
                }
            })
    }
}
