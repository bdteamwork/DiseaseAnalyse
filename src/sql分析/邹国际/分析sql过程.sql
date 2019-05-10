#各科室占百分比
SELECT
	department,
	COUNT(1) AS count
FROM
	illness_info
WHERE
	department != ''
GROUP BY
	department

#根据年龄
SELECT
	department,
age,
	COUNT(1) AS count
FROM
	illness_info
WHERE
	age != '未知'
	AND department !=''
GROUP BY
	department,age

#根据性别
SELECT
	department,
sex,
	COUNT(1) AS count
FROM
	illness_info
WHERE
 department !=''
AND sex !='未知'
GROUP BY
	department,sex
