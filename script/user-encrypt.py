import pymysql
import os
import base64
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
from dotenv import load_dotenv

load_dotenv()

DB_CONFIG = {
    "host": os.getenv("DB_HOST"),
    "port": int(os.getenv("DB_PORT", 3306)),
    "user": os.getenv("DB_USER"),
    "password": os.getenv("DB_PASSWORD"),
    "db": os.getenv("DB_NAME"),
    "charset": os.getenv("DB_CHARSET", "utf8mb4"),
}

SECRET_KEY = os.getenv("SECRET_KEY", os.getenv('ENCRYPTION_KEY')
BLOCK_SIZE = 16

def encrypt(text, key):
    if text is None:
        return None
    cipher = AES.new(key.encode(), AES.MODE_ECB)
    padded = pad(text.encode(), BLOCK_SIZE)
    encrypted = cipher.encrypt(padded)
    return base64.b64encode(encrypted).decode()

def decrypt(encrypted_b64, key):
    if encrypted_b64 is None:
        return None
    cipher = AES.new(key.encode(), AES.MODE_ECB)
    decrypted = cipher.decrypt(base64.b64decode(encrypted_b64))
    return unpad(decrypted, BLOCK_SIZE).decode()

def encrypt_user_data():
    conn = pymysql.connect(**DB_CONFIG)
    with conn.cursor() as cursor:
        cursor.execute("SELECT id, email, refresh_token, value FROM users")
        users = cursor.fetchall()

        for user_id, email, refresh_token, value in users:
            encrypted_email = encrypt(email, SECRET_KEY)
            encrypted_token = encrypt(refresh_token, SECRET_KEY)
            encrypted_value = encrypt(value, SECRET_KEY)

            update_sql = """
                UPDATE user
                SET email = %s,
                    refresh_token = %s,
                    value = %s
                WHERE id = %s
            """
            cursor.execute(update_sql, (encrypted_email, encrypted_token, encrypted_value, user_id))
            print(f"✅ Encrypted and updated user {user_id}")

        conn.commit()
    conn.close()

if __name__ == "__main__":
    encrypt_user_data()
