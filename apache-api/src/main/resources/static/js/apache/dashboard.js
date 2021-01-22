/**
 *  File: dashboard.js
 *
 */
$(window).on('load', function () {
    DASHBOARD.mounted();
});

/**
 * Dashboard
 */
const DASHBOARD = {
    userInfo: {},
    cookieInfo: {},

    mounted() {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            DASHBOARD.cookieInfo = response

            if (response.rid !== '0') {
                window.location.href = '/user-info?id=' + response.uid
            }

            return response
        }).then(response => {
            if (response && response.uid) {
                return COMMON_API.getUserInfo(response.uid)
            }
            return null
        }).then(response => {
            if (response) {
                DASHBOARD.userInfo = response
                DASHBOARD.initPage()
            }
        })
    },

    initPage () {
        DASHBOARD.getAllUser()
        DASHBOARD.initChart()
    },

    getAllUser () {
        return REST.get('/vi/user/api/v1/get-all-user')
        .then(response => {
            if (response && response.length !== 0) {
                $('#userList').empty()
                response.forEach((item, index) => {
                    let statusHtml = '<span class="main-badge main-badge-fill main-badge-BADGE_COLOR">STATUS_VALUE</span>'
                    switch (item.userStatus) {
                        case CONSTANT.USER_STATUS.ACTIVE.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'success')
                            break
                        case CONSTANT.USER_STATUS.BLOCKED.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'danger')
                            break
                    }
                    statusHtml = statusHtml.replace('STATUS_VALUE', COMMON.getValueByKey(CONSTANT.USER_STATUS, 'id', 'name', item.userStatus))

                    $('#userList').append(`
                                            <tr style="width: 100%">
                                                <td>${index + 1}</td>
                                                <td style="word-break: break-word;">${item.fullName}</td>
                                                <td style="word-break: break-word;">${item.username}</td>
                                                <td style="word-break: break-word;">${item.serverName}</td>
                                                <td style="word-break: break-word;">${statusHtml}</td>
                                                <td style="width: 13%;word-break: break-word;">
                                                    <button class="btn-main" onclick="window.location.href = '/user-info?id=${item.userId}'">View Info</button>
                                                </td>
                                            </tr>`)
                })
            } else {
                COMMON.toastMsg('List user is empty!', CONSTANT.MSG_TYPE.WARNING)
            }
        }).catch(error => {
                console.log(error)
            COMMON.toastMsg('System proceed failed.', CONSTANT.MSG_TYPE.ERROR)
        })
    },

    initChart() {
        REST.get('/vi/log/api/v1/get-analysis-log').then(response => {
            if (response && response.length !== 0)
                response = response.map(item => {
                    item.color = DASHBOARD.getRandomRgb()
                    return item
                })

            var ctx = document.getElementById('chart-area').getContext('2d');
            window.myPie = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    datasets: [
                        {
                            data: response.map(item => item.totalLog),
                            backgroundColor: response.map(item => item.color),
                        },
                    ],
                    labels: response.map(item => item.serverName),
                },
                options: {
                    plugins: {
                        datalabels: {
                            formatter: (value) => {
                                return value + '%';
                            }
                        }
                    }
                }
            })
        })

    },

    getRandomRgb() {
        let num = Math.round(0xffffff * Math.random());
        let r = num >> 16;
        let g = num >> 8 & 255;
        let b = num & 255;
        return 'rgb(' + r + ', ' + g + ', ' + b + ')';
    }
}