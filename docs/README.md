# Dexter User Guide

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

add deadlines to the task manager and returns a message showing the result

example: `deadline Buy a drink /by 2025-01-02`

```
expected output
____________________________________________________________

Got it. I've added this task:
[D][] Buy a drink (by: Jan 2 2025)
Now you have 2 tasks in the list.
____________________________________________________________
```

## Adding events

add events to the task manager and returns a message showing the result

example: `event Host a birthday party /from 2024-12-12 4pm Chocolate Factory /to 6pm The chocolate here is good`

```
expected output
____________________________________________________________
Got it. I've added this task:
[E][] Host a birthday party (from: Dec 12 2024 4pm Chocolate Factory) (to:6pm) The chocolate here is good
Now you have 2 tasks in the list.
____________________________________________________________
```

## Adding todo items

add todo items to the task manager and returns a message showing the result

example: `todo go for a run`

```
expected output
____________________________________________________________
Got it. I've added this task:
[T][] go for a run
Now you have 2 tasks in the list.
____________________________________________________________
```

## Deleting tasks

// remove tasks which are no longer necessary and shows result

example: `delete 4`

```
expected output
____________________________________________________________
Noted. I've removed this task:
[T][] go for a run Now you have 
2 tasks in the list.
____________________________________________________________
```


## Search for keyword in the list of tasks

// find tasks with the keyword mentioned and prints result

example `find dispose`

```
expected output
____________________________________________________________
Here are the matching tasks in your list
1. [T][] dispose of trash
2. [D][] dispose body (by:Jan 1 2019) 
____________________________________________________________
```

## Find tasks occurring on specified date

// find tasks happening on the date and displays to user

example `due 2025-01-01`

```
expected output
____________________________________________________________
[E][] Build a kennel (from: Jan 1 2025 3pm Dexter's House 8am) (to: 6pm) My cozy house
[D][X] Buy a new keyboard (by: Jan 1 2025)
____________________________________________________________
```

## Mark a task as done

// Marks task and returns result

example `mark 4`

```
expected output
____________________________________________________________
Nice! I've marked this task as done:
[T][X] dispose of trash
____________________________________________________________
```

## Mark a task as unfinished

// Unmarks task and returns result

example `unmark 4`

```
expected output
____________________________________________________________
OK, I've marked this task as not done yet:
[T][] dispose of trash
____________________________________________________________
```


## List out the current tasks

// Prints a list of the current tasks which may or may not be done

example `list`

```
expected output
____________________________________________________________
1. [T][] dispose of trash
2. [D][] dispose body (by:Jan 1 2019) 
3. [E][] Build a kennel (from: Jan 1 2025 8am Dexter's House) (to: 6pm) My cozy house
4. [D][X] Buy a new keyboard (by: Jan 1 2025)
____________________________________________________________
```


## Retrieve events 

// Retrieves list of events for user to view details about the location

example `retrieve event`

```
expected output
____________________________________________________________
Here is the Event List:
[E][] Build a kennel (from: Jan 1 2025 8am Dexter's House) (to: 6pm) My cozy house
[E][] Host a birthday party (from: Dec 12 2024 4pm Chocolate Factory) (to: 6pm) The chocolate here is good
____________________________________________________________
```

## Help function

// Returns a list of commands available for the user 

example `help`

```
expected output
____________________________________________________________
Here are the list of commands

todo [description]

deadline [description] /by [YYYY-MM-DD]

event [description] /from [YYYY-MM-DD] [startTime] [location] /to [endTime] [description about location]

mark [index] 

unmark [index]

due [YYYY-MM-DD]

find [keyword]

list

delete [index]

retrieve event 

bye
____________________________________________________________
```
