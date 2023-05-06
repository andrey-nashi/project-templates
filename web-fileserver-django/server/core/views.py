import json
from .services import *
from django.http import JsonResponse


class ApiFiles:

    STATUS_OK = 0
    STATUS_NO_REQUEST = 1
    STATUS_EXCEPTION = 2
    STATUS_BAD_REQUEST = 3
    STATUS_FILE_DUPLICATE = 4
    STATUS_FILE_NOT_FOUND = 5

    @staticmethod
    def _handle_request_GET(request, args):
        file_list =  FileStorageService.files_get_all()
        output = [ApiFiles.STATUS_OK, file_list]
        return output

    @staticmethod
    def _handle_request_POST(request, args):
        # ---- It is possible here to use django Forms to impose hard conditions
        # ---- to the received payload, yet since this project is for illustrative
        # ---- purposes, simpler checks are done.
        received_payload = request.FILES
        if "file" not in received_payload: return [ApiFiles.STATUS_BAD_REQUEST, None]
        file_name = received_payload["file"].name
        file_descriptor = received_payload["file"]
        result = FileStorageService.files_store(file_name, file_descriptor)
        if result: return [ApiFiles.STATUS_OK, file_name]
        else: return [ApiFiles.STATUS_FILE_DUPLICATE, file_name]


    @staticmethod
    def _handle_request_DELETE(request, args):
        received_payload = json.loads(request.body)
        if "file_name" not in received_payload: return [ApiFiles.STATUS_BAD_REQUEST, None]

        file_name = received_payload["file_name"]

        result = FileStorageService.files_delete(file_name)
        if result: return [ApiFiles.STATUS_OK, file_name]
        else: return [ApiFiles.STATUS_FILE_NOT_FOUND, file_name]


    @staticmethod
    def handle_request(request, **args):
        try:
        #if True:

            if request.method == "GET": output = ApiFiles._handle_request_GET(request, args)
            elif request.method == "POST": output = ApiFiles._handle_request_POST(request, args)
            elif request.method == "DELETE": output = ApiFiles._handle_request_DELETE(request, args)
            else: output = [ApiFiles.STATUS_NO_REQUEST, None]
        except:
            output = [ApiFiles.STATUS_EXCEPTION, None]

        return JsonResponse(output, safe=False, json_dumps_params={'ensure_ascii': False})

