# Event Tracker Application with Widgets

A Kotlin-based Android application designed to help users track recurring events. The app offers a simple interface for creating, managing, and visualizing events, with a widget for quick access directly from the home screen.

## Features

### Event Management
- **Create Events**: Customize recurrence intervals (e.g., every 3 days, weekly, etc.).
- **Track Progress**: Record when an event is completed and automatically calculate the next occurrence based on the completion date.
- **Dynamic Scheduling**: Adjust schedules by marking events done earlier or later than planned.

### Widget Integration
- **Home Screen Widget**: Displays the latest event status and the next scheduled occurrence.
- **Quick Interaction**: Interact with your events without opening the app.

### Real-Time Updates
- When an event is updated, both the app and widget reflect the changes immediately.

## Example Use Case
For managing hair care routines:
1. Create an event titled **"Shampoo"**.
2. Set a recurrence of every 3 days.
3. After washing your hair, mark the event as done, and the app will calculate the next wash day.
4. If you wash your hair earlier or later than planned, the schedule dynamically adjusts.

## Technology Stack
- **UI Framework**: Jetpack Compose for a modern, declarative UI design.
- **Widget Development**: Jetpack Glance for clean and interactive widgets.
- **State Management**: ViewModel and LiveData for handling app state.

## Project Goal
The goal is to build a modular and extendable event tracker that can adapt to any recurring task or routine, ensuring an efficient and user-friendly experience. Future enhancements could include:
- Notifications
- Calendar integrations
- Advanced analytics
