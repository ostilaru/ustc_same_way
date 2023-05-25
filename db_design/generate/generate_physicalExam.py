import csv
import random
from faker import Faker

fake = Faker('zh_CN')

# 体检数据范围
height_range = (150, 190)
weight_range = (40, 100)
vision_range = (0.7, 1.5)
lung_capacity_range = (2000, 6000)
exam_date = "2023-06-01"  # 设置体检日期

values = []

with open('school_Student.csv', 'r', encoding='utf-8') as file:
    reader = csv.reader(file)
    for row in reader:
        student_id, name, gender, _, _, _, _, _, _,  _ = row
        height = round(random.uniform(*height_range), 2)
        weight = round(random.uniform(*weight_range), 2)
        vision = f"{round(random.uniform(*vision_range), 1)}-{round(random.uniform(*vision_range), 1)}"
        lung_capacity = random.randint(*lung_capacity_range)
        
        values.append(f"('{student_id}', '{name}', '{gender}', {height}, {weight}, '{vision}', {lung_capacity}, '{exam_date}')")

with open('physical_exam_inserts.sql', 'w', encoding='utf-8') as file:
    insert_query = f"INSERT INTO PhysicalExam (student_id, name, gender, height, weight, vision, lung_capacity, exam_date) VALUES \n"
    file.write(insert_query)
    file.write(",\n".join(values) + ";")
