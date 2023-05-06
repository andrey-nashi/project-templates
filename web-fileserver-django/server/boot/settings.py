import os

class LaunchSettings:

    def __init__(self,
                 debug: bool = True,
                 path_root_files: str = None,
                 path_root_logs: str = None,
                ):

        # ---- Django settings
        self.DEBUG = debug
        self.PATH_ROOT_FILES = path_root_files
        self.PATH_ROOT_LOGS = path_root_logs

    def import_from_environment(self):
        self.DEBUG = (os.getenv("FS_DEBUG") == "1")
        self.PATH_ROOT_FILES = os.getenv("FS_PATH_ROOT_FILES")
        self.PATH_ROOT_LOGS = os.getenv("FS_PATH_ROOT_LOGS")

LAUNCH_SETTINGS = LaunchSettings()
LAUNCH_SETTINGS.import_from_environment()

# -------------------------------------------------------------

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SECRET_KEY = '^ce9km@&nzou(h4deohd-enqfxh6*1g(bjjp6i7qn$l8$6kfus'

DEBUG = LAUNCH_SETTINGS.DEBUG

ALLOWED_HOSTS = ['*']

# -------------------------------------------------------------

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'rest_framework',
   # 'corsheaders',
    'core',
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
  #  'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
]

ROOT_URLCONF = 'boot.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'boot.wsgi.application'

# -------------------------------------------------------------

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}

# -------------------------------------------------------------

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]

# -------------------------------------------------------------

LANGUAGE_CODE = 'en-us'
TIME_ZONE = 'UTC'
USE_I18N = True
USE_L10N = True
USE_TZ = True

# -------------------------------------------------------------

STATIC_URL = '/static/'
CORS_ORIGIN_ALLOW_ALL = True