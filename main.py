from fastapi import FastAPI, UploadFile, Form
from fastapi.responses import HTMLResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from fastapi import Request
import shutil, os

from .utils import validate_image

app = FastAPI()

app.mount("/static", StaticFiles(directory="app/static"), name="static")
templates = Jinja2Templates(directory="app/templates")

@app.get("/", response_class=HTMLResponse)
async def root():
    return "<h2>Validator API Running</h2>"

@app.post("/validate")
async def validate(category: str = Form(...), file: UploadFile = None):
    if not file:
        return {"status": "error", "message": "No file uploaded"}

    upload_path = f"app/static/uploads/{file.filename}"
    os.makedirs("app/static/uploads", exist_ok=True)
    with open(upload_path, "wb") as buffer:
        shutil.copyfileobj(file.file, buffer)

    result = validate_image(category, upload_path)
    return result

@app.get("/admin", response_class=HTMLResponse)
async def admin(request: Request):
    return templates.TemplateResponse("admin.html", {"request": request})
