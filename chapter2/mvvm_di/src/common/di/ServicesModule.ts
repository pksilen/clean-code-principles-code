import { Module } from 'noicejs';


export default class ServicesModule extends Module {
    override async configure(): Promise<void> {
        this.bind('todoService')
            .toInstance(new TodoServiceImpl());

    }
}
