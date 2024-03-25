-- CreateTable
CREATE TABLE `SalesItem` (
    `id` VARCHAR(36) NOT NULL,
    `createdAtTimestampInMs` VARCHAR(191) NOT NULL,
    `name` VARCHAR(191) NOT NULL,
    `priceInCents` INTEGER NOT NULL,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `SalesItemImage` (
    `id` VARCHAR(36) NOT NULL,
    `rank` INTEGER NOT NULL,
    `url` VARCHAR(191) NOT NULL,
    `salesItemId` VARCHAR(191) NULL,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `SalesItemImage` ADD CONSTRAINT `SalesItemImage_salesItemId_fkey` FOREIGN KEY (`salesItemId`) REFERENCES `SalesItem`(`id`) ON DELETE SET NULL ON UPDATE CASCADE;
