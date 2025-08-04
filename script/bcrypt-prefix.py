import pymysql
import os
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

BCRYPT_PREFIX = "{bcrypt}"

def add_bcrypt_prefix_to_passwords():
    conn = pymysql.connect(**DB_CONFIG)
    with conn.cursor() as cursor:
        cursor.execute("SELECT id, password FROM users")
        users = cursor.fetchall()

        for user_id, password in users:
            if password and not password.startswith(BCRYPT_PREFIX):
                updated_password = BCRYPT_PREFIX + password
                update_sql = "UPDATE users SET password = %s WHERE id = %s"
                cursor.execute(update_sql, (updated_password, user_id))
                print(f"✅ Updated user {user_id}: {updated_password}")

        conn.commit()
    conn.close()

if __name__ == "__main__":
    add_bcrypt_prefix_to_passwords()
