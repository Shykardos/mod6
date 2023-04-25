WITH project_durations AS (
  SELECT project.ID,
  DATEDIFF('MONTH', project.START_DATE, project.FINISH_DATE) AS duration_in_months
  FROM project
),
project_costs AS (
  SELECT project.ID, SUM(worker.SALARY) * project_durations.duration_in_months AS cost
  FROM project
  JOIN project_durations ON project.ID = project_durations.ID
  JOIN project_worker ON project.ID = project_worker.PROJECT_ID
  JOIN worker ON project_worker.WORKER_ID = worker.ID
  GROUP BY project.ID, project_durations.duration_in_months
)
SELECT project.ID, project.CLIENT_ID, project.START_DATE, project.FINISH_DATE, project_costs.cost
FROM project
JOIN project_costs ON project.ID = project_costs.ID
ORDER BY project_costs.cost DESC;
