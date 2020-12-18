/**
 *  File: rest.js
 *
 */

/**
 * REST
 */
const REST = {
    HEADERS:{'Content-Type': 'application/json;charset=UTF-8'},
    AUTH_KEY: {
        USER_ID: '_uid',
        SESSION_ID: '_sid',
        ROLE_ID: '_rid'
    },
    COOKIES_INFO: {},
    JSON_CONTENT_TYPE: 'application/json;charset=UTF-8',
    BLOB_CONTENT_TYPE: 'blob',
    MULTIPART_FORM_DATA: 'multipart/form-data',
    HTTP_PROTOCOL: {
        POST: 'POST',
        GET: 'GET',
        DELETE: 'DELETE',
        PUT: 'PUT'
    },

    /**
     * GET
     */
    async get (_url = '', _data = {}, _header = {}, _contentType = '') {
        return await $.ajax({
            type: REST.HTTP_PROTOCOL.GET,
            url: _url,
            headers: REST.HEADERS,
            contentType: _contentType ? _contentType : REST.JSON_CONTENT_TYPE,
            data: _data,
            success: function (response) { return response },
            error: function (error) { return error }
        });
    },

    /**
     * POST
     */
    async post (_url = '', _data = {}, _header = {}, _contentType = '') {
        return await $.ajax({
            type: REST.HTTP_PROTOCOL.POST,
            url: _url,
            headers: REST.HEADERS,
            contentType: _contentType ? _contentType : REST.JSON_CONTENT_TYPE,
            data: JSON.stringify(_data),
            success: response => response,
            error: error => error
        });
    },

    /**
     * UPLOAD
     */
    upload: async function (_url = '', _data = new FormData(), _header = {}, _contentType = '') {
        return await $.ajax({
            type: REST.HTTP_PROTOCOL.POST,
            url: _url,
            enctype: REST.MULTIPART_FORM_DATA,
            data: _data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: response => response,
            error: error => error
        });
    },

    /**
     * DOWNLOAD
     */
    download: async function (_url = '', _data = new FormData(), _header = {}, _contentType = '') {
        return await $.ajax({
            type: REST.HTTP_PROTOCOL.GET,
            url: _url,
            data: _data,
            headers: REST.HEADERS,
            responseType: REST.BLOB_CONTENT_TYPE,
            success: response => response,
            error: error => error
        });
    },

    /**
     * DELETE
     */
    del: async function (_url = '', _data = {}, _header = {}, _contentType = '') {
        return await  $.ajax({
            type: REST.HTTP_PROTOCOL.DELETE,
            url: _url,
            headers: REST.HEADERS,
            contentType: _contentType ? _contentType : REST.JSON_CONTENT_TYPE,
            data: _data,
            success: response => response,
            error: error => error
        });
    }
}