class Settings {

    constructor() {
        this.API_URL = "http://localhost:9090"
        this.API_AUTH = this.API_URL + "/authenticate"
        this.API_USR_IMAGE_TBL = this.API_URL + "/image-list"
        this.API_USR_IMAGE = this.API_URL + "/image"
        this.API_USR_REGISTER = this.API_URL + "/register"
        this.API_USR_IMAGE_UPLOAD = this.API_URL + "/upload-image"
        this.API_OPENACCESS = this.API_URL + "/free"
    }
}

export default new Settings();