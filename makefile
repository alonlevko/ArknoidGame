# id 313545931
# user alonlev

compile: bin
	javac -cp biuoop-1.4.jar:.src -d bin src/ass7Game.java src/animations/Animation.java src/animations/AnimationRunner.java src/animations/CountdownAnimation.java src/animations/EndScreen.java src/animations/HighScoresAnimation.java src/animations/KeyPressStoppableAnimation.java src/animations/PauseScreen.java src/collidable/Alien.java src/collidable/Block.java src/collidable/Collidable.java src/collidable/CollisionInfo.java src/collidable/Edges.java src/collidable/HitNotifier.java src/collidable/Paddle.java src/collidable/Sprite.java src/counters/AlienFormation.java src/counters/BallRemover.java src/counters/BlockRemover.java src/counters/Counter.java src/counters/HitListener.java  src/counters/ScoreTrackingListener.java src/game/GameEnviroment.java  src/game/GameFlow.java src/game/GameLevel.java src/game/LevelIndicator.java src/game/LivesIndicator.java src/game/ScoreIndicator.java src/game/SpriteCollection.java src/geometry/Ball.java src/geometry/Line.java src/geometry/Point.java src/geometry/Rectangle.java src/geometry/Velocity.java  src/levelinformation/BlockFactory.java src/levelinformation/BackGround.java src/levelinformation/BlockCreator.java src/levelinformation/SpaceLevel.java src/utils/HighScoresTable.java  src/utils/Menu.java src/utils/MenuAnimation.java src/utils/MenuBackground.java src/utils/QuitTask.java src/utils/ScoreInfo.java src/utils/Selection.java src/utils/ShowHiScoresTask.java src/utils/Task.java

run:
	java -cp biuoop-1.4.jar:bin:resources space_invaders
jar:
	jar cfm  space-invaders.jar ./manifest.txt  -C bin/ . -C resources/ .
bin:
	mkdir bin
