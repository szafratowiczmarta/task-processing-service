# task-processing-service

# Steps to run application:
1. Download additional sources (init.sql and docker-compose-cdq.yml) to your Desktop. Create a directory named "sql" and put init.sql file in it.
2. Run command
   docker-compose -p cdq -f docker-compose-cdq.yml up --build -d --force-recreate
3. Check if postgres database is up and running and wheter the public schema contains two tables (results and tasks).
4. Run Application (Run Application.main()).
5. Application provides five endpoints:
   GET localhost:7050/tasks                                        => to get all tasks
   GET localhost:7050/task/{id}                                    => to get task by id eg. localhost:7050/task/1
   GET localhost:7050/task/{id}/status                             => to get task status eg. localhost:7050/task/1/status
   GET localhost:7050/task/1/result                                => to get task result eg. localhost:7050/task/1/result
   POST http://localhost:7050/task?input={input}&pattern={pattern} => to create a task eg. http://localhost:7050/task?input=ABCDEFG&pattern=TDD

Notes:
In GlobalConstants class there is a field THREAD_SLEEP_MILLS is is set up to 3 seconds to simulate longer task processing.
