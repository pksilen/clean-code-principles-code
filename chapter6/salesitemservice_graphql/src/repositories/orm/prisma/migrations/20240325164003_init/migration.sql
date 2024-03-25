-- DropForeignKey
ALTER TABLE `SalesItemImage` DROP FOREIGN KEY `SalesItemImage_salesItemId_fkey`;

-- AddForeignKey
ALTER TABLE `SalesItemImage` ADD CONSTRAINT `SalesItemImage_salesItemId_fkey` FOREIGN KEY (`salesItemId`) REFERENCES `SalesItem`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
