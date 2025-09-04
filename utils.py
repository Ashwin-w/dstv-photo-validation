import os
from PIL import Image
import numpy as np

REFERENCE_DIR = "app/static"

def image_similarity(img1_path, img2_path):
    try:
        img1 = Image.open(img1_path).resize((64, 64)).convert("L")
        img2 = Image.open(img2_path).resize((64, 64)).convert("L")
        arr1, arr2 = np.array(img1), np.array(img2)
        diff = np.mean((arr1 - arr2) ** 2)
        similarity = max(0, 100 - diff / 100)
        return similarity
    except Exception:
        return 0

def validate_image(category, upload_path):
    ref_path = os.path.join(REFERENCE_DIR, f"{category}.jpg")
    if not os.path.exists(ref_path):
        return {"status": "error", "message": f"No reference for {category}"}

    similarity = image_similarity(upload_path, ref_path)
    passed = similarity > 60
    return {
        "status": "Match" if passed else "No Match",
        "similarity": round(similarity, 2)
    }
