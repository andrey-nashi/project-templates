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
# A bunch of start-to-end tests of the API
#-------------------------------------------------------------

def test01_get_all(message_sender: MessageSender):
    # ---- Realistically we want here to initialize the DB state
    # ---- with some controlled data. This is although trivial
    # ---- is a time consuming implementation, thus here we carry out
    # ---- tests for our simple case of the DB containing one element - 'cute-cat.jpg'
    # ---- To init DB copy server/db.sqlite3 -> docker/static/
    x = message_sender.fs_store_get_all()
    if x[0] == 0 and len(x[1]) == 1 and x[1][0] == "cute-cat.jpg":
        return True
    return False

def test02_upload_one(message_sender: MessageSender):
    x = message_sender.fs_store_upload("./gray-cat.jpg")
    x = message_sender.fs_store_get_all()
    if x[0] == 0 and "gray-cat.jpg" in x[1]:
        return True
    return False

def test03_delete_one(message_sender: MessageSender):
    # ---- Assume here that we have our initial state
    x = message_sender.fs_store_delete("cute-cat.jpg")
    x = message_sender.fs_store_get_all()
    if x[0] == 0 and "cute-cat.jpg" not in x[1]:
        return True
    return False


def test04_upload_non_existent(message_sender: MessageSender):
    # ---- Check what happens when we try to upload non-existing file
    x = message_sender.fs_store_upload("empty.jpg")
    if x[0] == -100:
        return True
    return False

def test05_delete_non_existent(message_sender: MessageSender):
    # ---- Check what happens if we request to delete file not on the server
    x = message_sender.fs_store_delete("none.jpg")
    if x[0] == 5:
        return True
    return False

TEST_SET = [
    test01_get_all,
    test02_upload_one,
    test03_delete_one,
    test04_upload_non_existent,
    test05_delete_non_existent
]

def run_all_tests():
    message_sender = MessageSender(SERVER_URL)
    for test in TEST_SET:
        print(test.__name__, test(message_sender))



if __name__ == "__main__":
    run_all_tests()

