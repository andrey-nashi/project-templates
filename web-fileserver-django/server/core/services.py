import os
import datetime
from .models import *
from django.forms.models import model_to_dict
from django.conf import settings

class FileStorageService:

    @staticmethod
    def files_get_all():
        """
        Returns list of 'file_name'
        :return:
        """
        query_result = FileStorage.objects.all()
        output = [model_to_dict(record) for record in query_result]
        # ---- Just take name, other fields not needed
        output = [f["file_name"] for f in output]
        return output

    @staticmethod
    def files_delete(file_name: str) -> bool:
        """
        Delete file by name
        - Returns True if all OK
        - Returns False if no file with the specified name
        :param file_name:
        :return:
        """
        q = FileStorage.objects.filter(file_name=file_name)
        if len(q) != 0:
            file_path = os.path.join(settings.LAUNCH_SETTINGS.PATH_ROOT_FILES, file_name)
            if os.path.exists(file_path):
                os.remove(file_path)
            q.delete()
            return True
        else:
            return False

    @staticmethod
    def files_store(file_name: str, file_descriptor) -> bool:
        """
        Will save file if is not duplicate (for illustrative purpose)
        - Returns True if all OK
        - Returns False if duplicate
        :param file_name:
        :param file_descriptor:
        :return:
        """

        q = FileStorage.objects.filter(file_name=file_name)
        # ---- Duplicated files - different strategies could be implemented
        # ---- Either save again regardless and overwrite existing file
        # ---- or not save at all, here, for illustrative purposes, let's not save it
        if len(q) != 0:
            return False

        # ---- Write to volume location
        file_path = os.path.join(settings.LAUNCH_SETTINGS.PATH_ROOT_FILES, file_name)
        with open(file_path, "wb+") as target:
            for chunk in file_descriptor.chunks():
                target.write(chunk)

        # ---- Update the db
        timestamp = datetime.datetime.now()
        file = FileStorage(
            file_name = file_name,
            file_path = file_path,
            upload_date = timestamp
        )

        file.save()

        return True