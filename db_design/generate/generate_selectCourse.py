import csv
import random

course_ids = list(range(1, 14))  # 课程ID列表
min_courses = 2  # 最少选课数量
max_courses = 5  # 最多选课数量

values = []

with open('school_Student.csv', 'r', encoding='utf-8') as file:
    reader = csv.reader(file)
    for row in reader:
        student_id, *_ = row
        selected_courses = random.sample(course_ids, random.randint(min_courses, max_courses)) # 随机选课

        for course_id in selected_courses:
            values.append(f"({course_id}, '{student_id}')")

with open('course_selection_inserts.sql', 'w', encoding='utf-8') as file:
    insert_query = f"INSERT INTO CourseSelection (course_id, student_id) VALUES \n"
    file.write(insert_query)
    file.write(",\n".join(values) + ";")
