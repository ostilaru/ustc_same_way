import random
from faker import Faker

# 创建一个Faker实例
fake = Faker('zh_CN')

class_department = [(200101, 1, 50), (200102, 1, 50), (200103, 1, 50),
                    (200201, 2, 100), (200202, 2, 100), 
                    (200301, 3, 80), (200302, 3, 80), (200303, 3, 90),
                    (200401, 4, 100), (200402, 4, 100), (200403, 4, 100),
                    (200501, 5, 50), (200502, 5, 50)]

values = []
num = 0

for class_id, department_id, student_count in class_department:
    num += 1
    teacher_id = f"TE{department_id}{str(num).zfill(3)}"
    teacher_name = fake.name()
    gender = random.choice(['男', '女'])
    contact_phone = fake.phone_number()
    contact_mail = fake.email()

    values.append(f"('{teacher_id}', '{teacher_name}', '{gender}', {department_id}, '{contact_phone}', '{contact_mail}')")

with open('teacher_inserts.sql', 'w', encoding='utf-8') as file:
    insert_query = f"INSERT INTO Teacher (teacher_id, teacher_name, gender, department_id, contact_phone, contact_mail) VALUES \n"
    file.write(insert_query)
    file.write(",\n".join(values) + ";")
