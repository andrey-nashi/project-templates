from django.db import models

class FileStorage(models.Model):
    file_name = models.CharField(primary_key=True, max_length=255)
    file_path = models.CharField(max_length=1024, blank=True, null=True)
    upload_date = models.DateTimeField(blank=True, null=True)


    class Meta:
        managed = False
        db_table = "files"