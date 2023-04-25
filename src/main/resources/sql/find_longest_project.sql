WITH project_durations AS (
  SELECT project.ID, project.CLIENT_ID, project.START_DATE, project.FINISH_DATE,
  DATEDIFF('MONTH', project.START_DATE, project.FINISH_DATE) AS duration_in_months
  FROM project
)
SELECT *
FROM project_durations
WHERE duration_in_months = (SELECT MAX(duration_in_months) FROM project_durations);
