import os
import json
import requests

#-------------------------------------------------------------
SERVER_URL = "http://0.0.0.0:8000/fs-store/"

SERVER_STATUS_OK = 0
SERVER_STATUS_NO_REQUEST = 1
SERVER_STATUS_EXCEPTION = 2
SERVER_STATUS_BAD_REQUEST = 3
SERVER_STATUS_FILE_DUPLICATE = 4
SERVER_STATUS_FILE_NOT_FOUND = 5

#-------------------------------------------------------------

class MessageSender:

    API_URL_GET_ALL = "files"
    API_URL_UPLOAD = "files"
    API_URL_DELETE = "files"

    CODE_FILE_NOT_EXIST = -100

    def __init__(self, server_url: str):
        self.server_url = server_url

    def fs_store_get_all(self) -> list:
        result = requests.get(self.server_url + MessageSender.API_URL_GET_ALL)
        result = result.json()
        return result

    def fs_store_delete(self, file_name: str) -> int:
        payload = {
            "file_name": file_name
        }
        result = requests.delete(self.server_url + MessageSender.API_URL_DELETE, data=json.dumps(payload))
        result = result.json()
        return result

    def fs_store_upload(self, file_path: str) -> int:
        # ---- File does not exist
        if not os.path.exists(file_path):
            return [MessageSender.CODE_FILE_NOT_EXIST, None]

        file_name = os.path.basename(file_path)
        file_ob = {"file": open(file_path, "rb"), "file_name": file_name}
        result = requests.post(self.server_url + MessageSender.API_URL_UPLOAD, files=file_ob)
        result = result.json()
        return result


#-------------------------------------------------------------

class Main:

    CODE_TABLE = {
        MessageSender.CODE_FILE_NOT_EXIST: "Status: Can't upload file - not found",
        SERVER_STATUS_OK: "Status: OK",
        SERVER_STATUS_NO_REQUEST: "Status: Unknown request type",
        SERVER_STATUS_EXCEPTION: "Status: Server exception",
        SERVER_STATUS_BAD_REQUEST: "Status: Incorrect payload format",
        SERVER_STATUS_FILE_DUPLICATE: "Status: Duplicate file on server",
        SERVER_STATUS_FILE_NOT_FOUND: "Status: Can't delete file - not found"
    }

    @staticmethod
    def launch():
        # ---- Maybe pass as argument
        ms = MessageSender(SERVER_URL)

        print("-------------------------------------------------")
        print("--- Challenge: File Server - Client ")
        print("--- List of commands")
        print("--- * get - get list of files on the server")
        print("--- * upload <path> - upload file to the server")
        print("--- * delete <name> - delete file by its name")
        print("--- * q - quit")
        print("-------------------------------------------------")

        while True:
            print(">>", end=" ")
            user_command = input()
            if user_command == "q": exit(0)
            if user_command.startswith("get"):
               result = ms.fs_store_get_all()
               flag = result[0]
               data = result[1]
               status = "Unknown" if flag not in Main.CODE_TABLE else Main.CODE_TABLE[flag]
               print(status)
               print("There are", len(data), "files on the server")
               for file_name in data:
                   print("-", file_name)
            if user_command.startswith("upload"):
                file_path = user_command.split(" ")[-1]
                result = ms.fs_store_upload(file_path)
                flag = result[0]
                data = result[1]
                status = "Unknown" if flag not in Main.CODE_TABLE else Main.CODE_TABLE[flag]
                print(status)
                print("Server returned: ", data)
            if user_command.startswith("delete"):
                file_name = user_command.split(" ")[-1]
                result = ms.fs_store_delete(file_name)
                flag = result[0]
                data = result[1]
                status = "Unknown" if flag not in Main.CODE_TABLE else Main.CODE_TABLE[flag]
                print(status)
                print("Server returned: ", data)

#-------------------------------------------------------------

if __name__ == "__main__":
    Main.launch()
