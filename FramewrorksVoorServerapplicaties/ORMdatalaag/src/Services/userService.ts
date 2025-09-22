import {DAO} from "../DAO";
import {AppUser} from "../entity/AppUser";
import {Repository} from "typeorm";
import {AppDataSource} from "../data-source";

const dao = new DAO();

export class UserService {
    private userRepository: Repository<AppUser>;
    constructor() {
        this.userRepository = AppDataSource.getRepository(AppUser);
    }
    async getAllUsers() {
        return await dao.getAllAppUsers();
    }

    async getUser(id: number) {
        return await dao.getAppUser(id);
    }

    async createUser(firstName: string, lastName: string, age: number) {
        await dao.setAppUser(firstName, lastName, age);
    }

    async updateUser(id: number, firstName: string, lastName: string, age: number) {
        await dao.updateAppUser(id, firstName, lastName, age);
    }

    async deleteUser(id: number) {
        await dao.deleteAppUser(id);
    }
    async addFriend(userId: number, friendId: number): Promise<AppUser> {
        const user = await this.userRepository.findOne({ where: { id: userId }, relations: ['friends'] });
        const friend = await this.userRepository.findOne({ where: { id: friendId } });
        if (user && friend) {
            user.friends.push(friend);
            return await this.userRepository.save(user);
        }
        throw new Error('User or friend not found');
    }
}
