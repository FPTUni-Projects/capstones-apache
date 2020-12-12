/**
 *  File: common.js
 *  @author khal
 */
$(window).on('load', function () {
    COMMON.mounted()
})

/**
 * COMMON
 */
const COMMON = {
    mounted () {
        COMMON.activeSidebarAndNavbar()
    },

    activeSidebarAndNavbar () {
        let menuNavLinkArr = $('.menu-nav-link')
        let menuNavItemArr = $('.menu-nav-item')
        let sidebarNameArr = $('.sidebar-name')

        let pathname = window.location.pathname

        for (let i = 0; i < menuNavLinkArr.length; i ++) {
            let dataName = $(menuNavLinkArr[i]).attr('data-name')
            let dataUri = $(menuNavLinkArr[i]).attr('data-uri')
            let dataUriArr = []
            let dataNameArr = []
            if (dataUri.indexOf(',') != -1) {
                dataUriArr = dataUri.split(',')
                dataNameArr = dataName.split(',')
            } else {
                dataUriArr.push(dataUri)
                dataNameArr.push(dataName)
            }

            for (let k = 0; k < dataUriArr.length; k++) {
                if (pathname.includes(dataUriArr[k])) {
                    // active sidebar
                    $('.menu-nav-item').each(function () {
                        $(this).removeClass('active')
                    })
                    $(menuNavItemArr[i]).addClass('active')

                    // active navbar
                    $('#navbarTitle').html(dataNameArr[k]).text()
                    $('#navbarTitle').attr('href', pathname)
                }
            }
        }
    },

    removeSidebar (_role) {
        switch (_role) {
            case CONSTANT.ROLES.ADMIN.id: break;
            case CONSTANT.ROLES.MANAGER.id:
                $('#createUserMenu').remove()
                break;
            case CONSTANT.ROLES.FINANCE.id:
                $('#createUserMenu').remove()
                break;
            case CONSTANT.ROLES.BUILDING.id:
                $('#createUserMenu').remove()
                break;
        }
    },

    formatCurrency (number = 0) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(number)
    },

    toastMsg (_msg, _msgType, _icon = '', _autoClose = false) {
        $.notify({
            icon: _icon,
            message: _msg
        },{
            type: _msgType,
            timer: _autoClose ? 0 : 1000
        });
    },

    getValueByKey (_data, _key1, _key2, _val) {
        let results = Object.values(_data).filter(item => item[_key1] == _val)
        return results.length !== 0 ? results[0][_key2] : ''
    },

    randomText (_length) {
        const c = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
        return _length <= 0 ? '' : [...Array(_length)].map(_ => c[~~(Math.random()*c.length)]).join('')
    }
}