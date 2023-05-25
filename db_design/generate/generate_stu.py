import random
from faker import Faker

# 创建一个Faker实例
fake = Faker('zh_CN')

class_department = [(200101, 1, 50), (200102, 1, 50), (200103, 1, 50),
                    (200201, 2, 100), (200202, 2, 100), 
                    (200301, 3, 80), (200302, 3, 80), (200303, 3, 90),
                    (200401, 4, 100), (200402, 4, 100), (200403, 4, 100),
                    (200501, 5, 50), (200502, 5, 50)]

statuses = ['在校']

values = []

for class_id, department_id, student_count in class_department:
    for i in range(1, student_count + 1):
        student_id = f"PB{class_id}{str(i).zfill(3)}"
        name = fake.name()
        gender = random.choice(['男', '女'])
        enrollment_date = "2020-09-01"  # 入学日期都是2020年9月
        graduation_date = "2024-06-01"  # 毕业日期都是2024年6月
        contact_phone = fake.phone_number()
        contact_mail = fake.email()
        status = random.choice(statuses)

        values.append(f"('{student_id}', '{name}', '{gender}', {department_id}, {class_id}, '{enrollment_date}', '{graduation_date}', '{contact_phone}', '{contact_mail}', '{status}')")

with open('student_inserts.sql', 'w', encoding='utf-8') as file:
    insert_query = f"INSERT INTO Student (student_id, name, gender, department_id, class_id, enrollment_date, graduation_date, contact_phone, contact_mail, status) VALUES \n"
    file.write(insert_query)
    file.write(",\n".join(values) + ";")
