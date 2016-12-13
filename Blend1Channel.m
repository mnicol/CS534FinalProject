function result = Blend1Channel(source, target, mask, A)
[r, c, ~] = size(target);
b = zeros(length(A(:,1)), 1);
%We're in index space again....
for i = 1:r*c
    if(mask(i))
        b(i) = 4*source(i) - source(i-1) - source(i+1) - source(i+r) - source(i-r);
    else
        b(i) = target(i);
    end
end

%Note the direction of the slash. The other slash is the wrong slash. 
result = A \ b;