# Algorithm Design and Analysis (ADA) - NOVA FCT ⚙️

**Bachelor in Computer Science and Engineering (LEI) | 3rd Year**

This repository contains the projects and practical exercises developed for the Algorithm Design and Analysis (Análise e Desenho de Algoritmos) course at the Faculty of Sciences and Technology of the NOVA University of Lisbon (NOVA FCT).

## ✍️ Authors
- **Tomás Alves** (Student No. 68681)
- **Miguel Carmo** (Student No. 65871)

## 📌 About the Course and Repository

The ADA course focuses on the theoretical and practical aspects of algorithm design, analyzing computational complexity, and applying advanced algorithmic strategies. 

This repository includes a collection of smaller practical algorithms developed throughout the semester, but its core focus is on the two main course projects:

### 1. Project 1: Crystal Castle (Dynamic Programming) 🏰
* **Description:** A pathfinding optimization problem on a 2D grid where a character must navigate from the top-left to the bottom-right corner while avoiding obstacles (`#`). The character has movement restrictions: a maximum number of consecutive basic moves and a maximum number of special jumps.
* **Key Features:**
  * Uses a 4-dimensional Dynamic Programming state array: `dataTable[row][col][consecutiveMoves][jumps]`.
  * Calculates the total number of valid paths modulo `1_000_000_007L`.
  * Handles standard movements (Right, Down) and special jump mechanics (Double Down, Right-Down, Left-Down) based on tile types.
* **Documentation:**
  * [Project 1 Statement (PDF)](./src/Project1/Project1.pdf)
* **Location:**
  * [Project 1 Code (CODE)](./src/Project1/)

### 2. Project 2: Magic Beams (Graphs & Topological Sorting) 🪄
* **Description:** A dependency resolution problem where the objective is to clear a specific corridor of magic beams without triggering a false alarm or a disaster. The algorithm must find a valid sequence to safely remove the beams.
* **Key Features:**
  * Models beam intersections and blocking mechanisms by building a Dependency Graph.
  * Utilizes Breadth-First Search (BFS) to trace beam paths (`Direction.N, S, E, W`) and identify which beams block others.
  * Applies Kahn's Algorithm (Topological Sorting) to process in-degrees and determine the exact safe removal order.
* **Documentation:**
  * [Project 2 Statement (PDF)](./src/Project2/Project2.pdf)
* **Location:**
  * [Project 2 Code (CODE)](./src/Project2/)

### 3. Practical Exercises 🧩
* **Description:** A set of smaller algorithm design challenges developed to practice specific techniques such as Binary Search, Graph Reachability, and logical deduction.
* **Location:**
  * [Project 1 Code (CODE)](./src/PraticalExs)

## 📄 License
 
Copyright © 2026 **Alvesss04**. All Rights Reserved.
