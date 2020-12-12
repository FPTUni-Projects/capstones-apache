/**
 *  File: constant.js
 *  @author khal
 */

/**
 * CONSTANT
 */
const CONSTANT = {

    MSG_TYPE: {
        INFO: 'info',
        WARNING: 'warning',
        ERROR: 'danger',
        SUCCESS: 'success'
    },

    CREATE_ORDER_MODE: {
        CREATE: '0',
        EDIT: '1'
    },

    ROLES: {
        ADMIN: {id: '0', name: 'Administrator'},
        MANAGER: {id: '1', name: 'Phòng Quản Lý'},
        FINANCE: {id: '2', name: 'Phòng Kinh Doanh'},
        BUILDING: {id: '3', name: 'Phòng Thi Công'}
    },

    ORDER_STATUS: {
        NEW_ORDER: {id: '0', name: 'Tạo mới'},
        MANUFACTURING: {id: '1', name: 'Chuẩn bị sản xuất'},
        APPROVAL: {id: '2', name: 'Xác nhận'},
        BUILDING: {id: '3', name: 'Thi công'},
        FINISHED: {id: '4', name: 'Hoàn thành'}
    }

}