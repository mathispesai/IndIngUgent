import { Server } from "socket.io";

export const socketEvents = (io: Server) => {
    io.on('connection', (socket) => {
        console.log('User connected');
        socket.on('updateWorkout', (data) => {
            console.log('Workout update:', data);
            io.emit('workoutUpdate', data);
        });

        socket.on('disconnect', () => {
            console.log('User disconnected');
        });
    });
};
