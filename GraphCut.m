function [ img ] = GraphCut( img, backgroundBox, JND )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    [m,n,k] = size(img);
    img = rgb2lab(img);
    foreBackGround = zeros(m,n,k);
    
    foreBackGround(:,:,1) = backgroundBox; %0 is background 1 is foreground
    
    changes = 1;
    
    while (changes) 
        changes = 0;
        for i=1:m
            for j=1:n
                if(foreBackGround(i,j,1) == 1)
                    continue;
                end
                if(i-1 > 0 && foreBackGround(i-1,j,2) == 0 && foreBackGround(i-1,j,1) == 1)
                    if(comparePixel(img(i,j,:), img(i-1,j,:), JND))
                        changes = 1;
                        foreBackGround(i-1,j,2) = 1;
                        foreBackGround(i-1,j,1) = 0;
                    end
                end
                if(i+1 < m && foreBackGround(i+1,j,2) == 0 && foreBackGround(i+1,j,1) == 1)
                    if(comparePixel(img(i,j,:), img(i+1,j,:), JND))
                        changes = 1;
                        foreBackGround(i+1,j,2) = 1;
                        foreBackGround(i+1,j,1) = 0;
                    end                    
                end
                if(i-1 > 0 && j-1 > 0 && foreBackGround(i-1,j-1,2) == 0 && foreBackGround(i-1,j-1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i-1,j-1,:), JND))
                        changes = 1;
                        foreBackGround(i-1,j-1,2) = 1;
                        foreBackGround(i-1,j-1,1) = 0;
                    end                    
                end
                if(i-1 > 0 && j+1 < n && foreBackGround(i-1,j+1,2) == 0 && foreBackGround(i-1,j+1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i-1,j+1,:), JND))
                        changes = 1;
                        foreBackGround(i-1,j+1,2) = 1;
                        foreBackGround(i-1,j+1,1) = 0;
                    end                    
                end
                if(i+1 < m && j-1 > 0 && foreBackGround(i+1,j-1,2) == 0 && foreBackGround(i+1,j-1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i+1,j-1,:), JND))
                        changes = 1;
                        foreBackGround(i+1,j-1,2) = 1;
                        foreBackGround(i+1,j-1,1) = 0;
                    end                    
                end
                if(i+1 < m && j+1 < n && foreBackGround(i+1,j+1,2) == 0 && foreBackGround(i+1,j+1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i+1,j+1,:), JND))
                        changes = 1;
                        foreBackGround(i+1,j+1,2) = 1;
                        foreBackGround(i+1,j+1,1) = 0;
                    end                      
                end
                if(j-1 > 0 && foreBackGround(i,j-1,2) == 0 && foreBackGround(i,j-1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i,j-1,:), JND))
                        changes = 1;
                        foreBackGround(i,j-1,2) = 1;
                        foreBackGround(i,j-1,1) = 0;
                    end                      
                end
                if(j+1 < n && foreBackGround(i,j+1,2) == 0 && foreBackGround(i,j+1,1) == 1)
                    if(comparePixel(img(i,j,:), img(i,j+1,:), JND))
                        changes = 1;
                        foreBackGround(i,j+1,2) = 1;
                        foreBackGround(i,j+1,1) = 0;
                    end                      
                end
            end
        end
        foreBackGround(:,:,2) = zeros(m,n);
    end
    
    img = lab2rgb(img);
    for i=1:m
        for j=1:n
            if(foreBackGround(i,j,1) == 0)
                img(i,j,:) = 0;
            end
        end
    end
    

end

