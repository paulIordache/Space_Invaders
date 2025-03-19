# 🌌 Star Wars-Inspired Space Invaders Game 🚀

This project is a **Star Wars**-inspired space game, combining elements from **Space Invaders**! 🛸 In this game, you shoot down enemy ships and advance to the next stage. At the end of each stage, you'll either see a **win screen** or a **lose screen** depending on the outcome. You can also choose from iconic characters like **Luke Skywalker**, **C-3PO**, **R2-D2**, and **Chewbacca**. 🌟

## 📁 Files

### 1. **Enemy.java**
The `Enemy` class represents the **opposing ships** coming toward the player. 

- **Movement**: The enemies move towards the player, typically in a formation or a pattern. They can move left and right across the screen, sometimes following a predictable path or with some random variations to make the gameplay more challenging.
- **AI Behavior**: The enemies might have basic AI. For example, they could shoot at the player or react to the player’s movement.
- **Collision Detection**: When a laser from the player hits an enemy, the enemy is destroyed, and the player scores points. This is detected and handled in this class.

### 2. **Laser.java**
The `Laser` class is responsible for the **laser projectiles** the player shoots at the enemies.

- **Laser Mechanics**: The laser moves vertically upward when fired from the player's ship. It continues to move until it goes off-screen or hits an enemy.
- **Collision Detection**: This class checks for collisions between the laser and enemy ships. When a laser collides with an enemy, the enemy is destroyed or damaged.
- **Rendering**: The laser is drawn on the screen, typically as a small line or rectangle, and its position is updated every frame.

### 3. **Main.java**
The `Main` class is the **entry point** of the game.

- **Game Initialization**: This is where the game window (using JavaFX) is created, and the game loop starts. It initializes all necessary components, such as the player, enemies, lasers, and background.
- **Game Loop**: This class manages the game loop, where it continually updates the game state, including player input, enemy movement, laser firing, and collisions.

### 4. **Player.java**
The `Player` class represents the **main player** character, such as Luke Skywalker or Chewbacca.

- **Movement**: The player can move left or right on the screen. This is typically done through keyboard inputs (e.g., arrow keys or WASD).
- **Shooting**: The player can shoot lasers towards enemies. When the player presses a button (such as the spacebar), a new `Laser` object is created and starts moving upward from the player’s position.
- **Health & Score**: The player may have health points (HP) that decrease when they are hit by enemy lasers or ships. The score increases when enemies are destroyed by lasers.

### 5. **SpaceInvaders.java**
The `SpaceInvaders` class contains the **core game logic**.

- **Stage Progression**: This class controls the flow of the game, advancing to the next stage after all enemies are defeated, or the game is over if the player loses.
- **Enemy Spawning**: The enemies are spawned in waves. Each wave may have more enemies or different types of enemies, making the game progressively harder.
- **Game Over & Win Conditions**: This class detects when the game should end, either with the player winning by defeating all enemies or losing when the player’s health reaches zero or they get hit by enemies.
- **Displaying Win/Loss Screens**: When the game ends, the `SpaceInvaders.java` class controls the transition to either the win screen or the loss screen.

### 6. **Universe.java**
The `Universe` class manages the **background** and overall **game world**.

- **Background**: This class could handle displaying a starry sky, space, or planets to create the space theme. It might also be responsible for scrolling backgrounds to make the player feel like they are traveling through space.
- **Game World**: It could set up the main game canvas, allowing objects like the player, enemies, and lasers to be drawn on the screen.
- **Background Music or Sound Effects**: The class could also manage playing background music or space-themed sound effects to enhance the game experience.

---

## 🎮 Features:
- Choose your character: **Luke Skywalker**, **C-3PO**, **R2-D2**, or **Chewbacca**! ✨
- Shoot down enemy ships and progress through stages. 🔫
- Separate screens for **win** and **lose** outcomes. 🏆❌
- Iconic **Star Wars** theme with JavaFX graphics. 🌠

---

## 🚀 How to Install & Clone the Project

### Prerequisites:
- **Java JDK 8** or later installed on your computer.
- **JavaFX SDK** (if not bundled with your JDK version).
- **IDE** like IntelliJ IDEA, Eclipse, or NetBeans (recommended for JavaFX development).

### Steps to Clone & Install:
1. **Clone the repository**:
   Open your terminal or command prompt and run the following command to clone the project:
   ```bash
   git clone https://github.com/yourusername/star-wars-space-invaders.git
