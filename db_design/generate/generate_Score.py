import random

min_score = 60  # 最低分
max_score = 100  # 最高分

with open('course_selection_inserts.sql', 'r', encoding='utf-8') as file:
    lines = file.readlines()

values = []
for line in lines:
    if 'INSERT INTO CourseSelection' in line or line.strip() == ';':
        continue
    course_id, student_id = line.strip('(),\n ').split(', ')
    score = random.randint(min_score, max_score)  # 生成随机分数
    values.append(f"({course_id}, {student_id}, {score})")

with open('score_inserts.sql', 'w', encoding='utf-8') as file:
    insert_query = f"INSERT INTO Score (course_id, student_id, score) VALUES \n"
    file.write(insert_query)
    file.write(",\n".join(values) + ";")
