function [ img ] = GraphCut2( img, backgroundBox, JND )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    [m,n,k] = size(img);
    img = rgb2lab(img);
    foreGround = zeros(m,n,k);
    backGround = zeros(m,n,k);
    
    for i=1:m
        for j=1:n
            if(backgroundBox(i,j) == 0)
                backGround(i,j,:) = img(i,j,:);
            else
                foreGround(i,j,:) = img(i,j,:);
            end
        end
    end
    
    for abc=1:1
        backGroundFormated = reshape(double(backGround(:,:,2:3)),m*n,2);
        foreGroundFormated = reshape(double(foreGround(:,:,2:3)),m*n,2);
        numColors = 3;
        
        [cluster_idx, cluster_center] = kmeans(backGroundFormated,numColors);
        [fg_cluster_idx, fg_cluster_center] = kmeans(foreGroundFormated, numColors*2);
        
        for i=1:numColors*2
            for j=1:numColors
                value = sqrt(fg_cluster_center(i,1)-cluster_center(j,1)^2 + fg_cluster_center(i,2)-cluster_center(j,2)^2);
                if(abs(value) < 4.5)
                    pixel_labels = reshape(fg_cluster_idx,m,n);
                    rgb_label = repmat(pixel_labels,[1 1 3]);
                    color = foreGround;
                    color(rgb_label ~= i) = 0;
                    foreGround(rgb_label == i) = 0;
                    backGround = backGround + color;
                end
            end
        end
    end
    
    backGround = lab2rgb(backGround);
    foreGround = lab2rgb(foreGround);
    img = foreGround;
end

