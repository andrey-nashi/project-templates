from django.urls import path

from .views import *

urlpatterns = [
    path("files", ApiFiles.handle_request),
]
