Engine - queues and iterates through events. Capable of queueing for future frames.
Event - conveys an intended change to be processed
EventSource - an Interface implemented by objects which are capable of generating Events
EventScheduler - a Thread which cycles through EventSources at a given interval and queues them to an eventEngine at a controlled rate
EventHandler - responsible for interpreting and resolving Events
