import axios from 'axios'

class FileUploadService {


    uploadFile(file) {
      let data = new FormData();

        data.append('file', file);
        data.append('name', file.name);
const config = { headers: { 'Content-Type': 'multipart/form-data' } };

       return axios.post('http://localhost:8080/uploadFile', data, config)
    }

}

export default new FileUploadService();
